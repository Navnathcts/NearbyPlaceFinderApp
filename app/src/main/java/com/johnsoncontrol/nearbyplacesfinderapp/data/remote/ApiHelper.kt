package com.johnsoncontrol.nearbyplacesfinderapp.data.remote

class ApiHelper(private val apiService: APIInterface?) {
    suspend fun getNearByPlaces(location: String, radius: Long, type: String, key: String) =
        apiService?.getNearByPlaces(
            location,
            radius,
            type,
            key
        )

    suspend fun getDistance(origins: String, destination: String, key: String) =
        apiService?.getDistance(origins, destination, key)

    suspend fun getPlaceDetails(placeId: String, key: String) = apiService?.getPlaceDetails(
        placeId,
        key
    )
}