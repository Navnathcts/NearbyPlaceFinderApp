package com.johnsoncontrol.nearbyplacesfinderapp.view.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.johnsoncontrol.nearbyplacesfinderapp.R
import com.johnsoncontrol.nearbyplacesfinderapp.utils.Constants
import com.johnsoncontrol.nearbyplacesfinderapp.utils.SharedPrefUtility
import kotlinx.android.synthetic.main.place_details_list_item.view.*
import com.johnsoncontrol.nearbyplacesfinderapp.view.model.PlaceDetailsResponse

class PlaceListAdapter : RecyclerView.Adapter<PlaceListAdapter.MyViewHolder>() {
    private var placeList: ArrayList<PlaceDetailsResponse>? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.place_details_list_item, parent, false)
        return MyViewHolder(
            itemView
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        placeList?.get(position)?.let {
            holder.itemView.apply {
                tvPlaceName.text = it.name
                tvDistance.text = when {
                    it.distanceInMiles.isNullOrBlank() -> "${it.distanceInKm} Km"
                    else -> "${it.distanceInMiles} Miles"
                }
                tvRating.text = it.review
                tvAddress.text = it.address
            }
        }
    }


    private fun convertMilesToKm(miles: String?): String {
        return (miles?.toFloat()?.div(0.621371))?.toFloat().toString()
    }
    override fun getItemCount(): Int = placeList?.size ?: 0

    fun setNearbyPlacesList(placeList: ArrayList<PlaceDetailsResponse>?) {
        this.placeList = placeList
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}