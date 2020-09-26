package com.johnsoncontrol.nearbyplacesfinderapp.data.repository

import com.johnsoncontrol.nearbyplacesfinderapp.data.remote.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {
    suspend fun getNearByPlaces(location: String, radius: Long, type: String, key: String) =
        apiHelper.getNearByPlaces(
            location,
            radius,
            type,
            key
        )

    suspend fun getDistance(origins: String, destination: String, key: String) =
        apiHelper.getDistance(origins, destination, key)

    suspend fun getPlaceDetails(placeId: String, key: String) =
        apiHelper.getPlaceDetails(placeId, key)
}