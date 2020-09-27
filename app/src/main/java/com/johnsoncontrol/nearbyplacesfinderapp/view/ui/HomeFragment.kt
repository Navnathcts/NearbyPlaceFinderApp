package com.johnsoncontrol.nearbyplacesfinderapp.view.ui

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.johnsoncontrol.nearbyplacesfinderapp.R
import com.johnsoncontrol.nearbyplacesfinderapp.data.remote.ApiClient
import com.johnsoncontrol.nearbyplacesfinderapp.data.remote.ApiHelper
import com.johnsoncontrol.nearbyplacesfinderapp.data.response_model.Resource
import com.johnsoncontrol.nearbyplacesfinderapp.utils.*
import com.johnsoncontrol.nearbyplacesfinderapp.view.model.PlaceDetailsResponse
import com.johnsoncontrol.nearbyplacesfinderapp.view.viewmodel.HomeViewModel
import com.johnsoncontrol.nearbyplacesfinderapp.view.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment(), GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener {
    private lateinit var homeViewModel: HomeViewModel
    private var placeCategory: String? = null
    private var mGoogleApiClient: GoogleApiClient? = null
    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null
    private var radius = Constants.RADIUS_5K.toLong()
    private val TAG = "HomeFragment"
    private var Permission_is_granted = false
    private var placeListAdapter: PlaceListAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        placeCategory = arguments?.let {
            HomeFragmentArgs.fromBundle(
                it
            ).fragmentType
        }
            ?: Constants.ALL_PLACE_TYPE
        homeViewModel = ViewModelProviders.of(
            this@HomeFragment,
            ViewModelFactory(
                ApiHelper(
                    ApiClient.apiService
                )
            )
        ).get(HomeViewModel::class.java)
        mFusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())
        mGoogleApiClient = GoogleApiClient.Builder(requireActivity())
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .build()
        placeListAdapter =
            PlaceListAdapter()
        Permission_is_granted = SharedPrefUtility.getBooleanValue(
            requireActivity(),
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        radius = SharedPrefUtility.getStringValue(requireActivity(), Constants.KEY_RADIUS_VALUE)
            ?.toLong() ?: Constants.RADIUS_5K.toLong()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        when {
            Permission_is_granted -> getUserLocation()
        }
    }

    private fun setupUI() {
        recyclerView?.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = placeListAdapter
        }
    }

    override fun onConnected(bundle: Bundle?) {
        Log.i("google api client", "coonected")
    }

    override fun onConnectionSuspended(i: Int) {
        Log.i(TAG, "Connection Suspended")
        mGoogleApiClient?.connect()
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        Log.i(TAG, "Connection failed. Error: " + connectionResult.errorCode)
    }

    override fun onStart() {
        Log.i("on start", "true")
        super.onStart()
        if (mGoogleApiClient != null) {
            mGoogleApiClient?.connect()
        }
    }

    private fun getUserLocation() {
        mFusedLocationProviderClient?.lastLocation
            ?.addOnSuccessListener(
                requireActivity()
            ) { location ->
                when {
                    location != null -> {
                        homeViewModel.getNearbyPlaces(
                            location.latitude.toString() + "," + location.longitude,
                            radius,
                            placeCategory ?: "",
                            Constants.GOOGLE_PLACE_API_KEY,
                            SharedPrefUtility.getBooleanValue(
                                requireActivity(),
                                Constants.KEY_SHOW_BY_MILES
                            )
                        ).observe(requireActivity(), Observer {
                            processPlaceDetailsResponse(it)
                        })

                    }
                    else -> {
                        tvErrorView?.showErrorMessage(getString(R.string.error_fetching_location))
                    }
                }
            }

    }

    private fun processPlaceDetailsResponse(it: Resource<ArrayList<PlaceDetailsResponse>?>) {
        when (it.status) {
            Status.SUCCESS -> {
                recyclerView?.showView()
                tvErrorView.hideView()
                pbLoader?.hideView()
                placeListAdapter?.setNearbyPlacesList(it.data)
            }
            Status.ERROR -> {
                recyclerView?.showView()
                pbLoader?.hideView()
                tvErrorView.apply {
                    text = it.message
                    showView()
                }
            }
            Status.LOADING -> {
                recyclerView?.hideView()
                tvErrorView.hideView()
                pbLoader?.showView()
            }
        }
    }

}
