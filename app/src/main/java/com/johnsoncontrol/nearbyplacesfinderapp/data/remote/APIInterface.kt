package com.johnsoncontrol.nearbyplacesfinderapp.data.remote

import com.johnsoncontrol.nearbyplacesfinderapp.data.response_model.DistanceResponse
import com.johnsoncontrol.nearbyplacesfinderapp.data.response_model.PlacesDetails
import com.johnsoncontrol.nearbyplacesfinderapp.data.response_model.PlacesListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface APIInterface {
    @GET("place/nearbysearch/json?")
    suspend fun getNearByPlaces(
        @Query(
            value = "location",
            encoded = true
        ) location: String?,
        @Query(value = "radius", encoded = true) radius: Long,
        @Query(value = "type", encoded = true) type: String?,
        @Query(value = "key", encoded = true) key: String?
    ): PlacesListResponse?

    @GET("distancematrix/json?")
    suspend fun getDistance(
        @Query(
            value = "origins",
            encoded = true
        ) origins: String?,
        @Query(
            value = "destinations",
            encoded = true
        ) destinations: String?,
        @Query(value = "key", encoded = true) key: String?
    ): DistanceResponse?

    @GET("place/details/json?")
    suspend fun getPlaceDetails(
        @Query(
            value = "placeid",
            encoded = true
        ) placeid: String?,
        @Query(
            value = "key",
            encoded = true
        ) key: String?
    ): PlacesDetails?
}