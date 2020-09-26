package com.johnsoncontrol.nearbyplacesfinderapp.view.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.johnsoncontrol.nearbyplacesfinderapp.data.repository.MainRepository
import com.johnsoncontrol.nearbyplacesfinderapp.data.response_model.DistanceResponse
import com.johnsoncontrol.nearbyplacesfinderapp.data.response_model.Resource
import com.johnsoncontrol.nearbyplacesfinderapp.utils.Constants
import com.johnsoncontrol.nearbyplacesfinderapp.utils.Constants.STATUS_OK
import com.johnsoncontrol.nearbyplacesfinderapp.view.model.PlaceDetailsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import kotlin.coroutines.suspendCoroutine

class HomeViewModel(private val mainRepository: MainRepository) : ViewModel() {
    val placeDetailsList: ArrayList<PlaceDetailsResponse>? = arrayListOf()
    fun getNearbyPlaces(
        location: String,
        radius: Long,
        type: String,
        key: String,
        isShowByMilesSelected: Boolean = false
    ) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))
            try {
                withContext(Dispatchers.IO) {
                    placeDetailsList?.clear()
                    mainRepository.getNearByPlaces(location, radius, type, key)?.customA?.forEach {
                        val distance = async {
                            Log.d("Navnath", "distance call started for " + it.place_id)
                            mainRepository.getDistance(
                                location,
                                it.geometry?.locationA?.lat.toString() + "," + it.geometry?.locationA?.lng,
                                Constants.GOOGLE_PLACE_API_KEY
                            )
                        }
                        val details = async {
                            Log.d("Navnath", "place details call started for " + it.place_id)
                            mainRepository.getPlaceDetails(
                                it.place_id + "",
                                Constants.GOOGLE_PLACE_API_KEY
                            )
                        }
                        when {
                            distance.await()?.status.equals(STATUS_OK) && details.await()?.status.equals(STATUS_OK) -> {
                                val placeDetailsModel = PlaceDetailsResponse()
                                placeDetailsModel.name = it.name
                                details.await()?.result?.let {
                                    placeDetailsModel.address = it.formatted_adress
                                    it.reviews?.let {
                                        if (it.isNotEmpty()) {
                                            placeDetailsModel.review = it.get(0).text
                                        }
                                    }
                                }

                                //distance information
                                distance.await()?.rows?.forEach {
                                    it.elements?.forEach {
                                        val distanceValue =
                                            it.distance?.text?.split(Constants.SPACE)?.get(0)
                                        when {
                                            isShowByMilesSelected -> placeDetailsModel.distanceInMiles =
                                                distanceValue
                                            else -> placeDetailsModel.distanceInKm =
                                                convertMilesToKm(distanceValue)
                                        }
                                        return@forEach
                                    }
                                    return@forEach
                                }
                                placeDetailsList?.add(placeDetailsModel)
                            }
                            else -> {
                                emit(
                                    Resource.error(
                                        data = null,
                                        message = "Error Occurred!"
                                    )
                                )
                            }
                        }
                    }
                    emit(Resource.success(placeDetailsList))
                }
            } catch (exception: Exception) {
                emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
            }
        }

    private fun convertMilesToKm(miles: String?): String {
        return (miles?.toFloat()?.div(0.621371))?.toFloat().toString()
    }
}