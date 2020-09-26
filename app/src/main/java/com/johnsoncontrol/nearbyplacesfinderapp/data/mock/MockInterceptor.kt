package com.johnsoncontrol.nearbyplacesfinderapp.data.mock

import com.johnsoncontrol.nearbyplacesfinderapp.BuildConfig
import okhttp3.*
import java.io.InputStream

class MockInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (BuildConfig.DEBUG) {
            val uri = chain.request().url().uri().toString()
            val responseString = when {
                uri.contains("nearbysearch") -> getJsonResponse(getNearbyPlaces())
                uri.contains("distancematrix") -> getJsonResponse(getDistance())
                uri.contains("details") -> getJsonResponse(getPlaceDetails())
                else -> ""
            }

            return chain.proceed(chain.request())
                .newBuilder()
                .code(200)
                .protocol(Protocol.HTTP_2)
                .message(responseString)
                .body(
                    ResponseBody.create(
                        MediaType.parse("application/json"),
                        responseString.toByteArray()
                    )
                )
                .addHeader("content-type", "application/json")
                .build()
        } else {
            //just to be on safe side.
            throw IllegalAccessError(
                "MockInterceptor is only meant for Testing Purposes and " +
                        "bound to be used only with DEBUG mode"
            )
        }
    }

    private fun getJsonResponse(fileName: String): String {
        return Thread.currentThread().contextClassLoader.getResourceAsStream(fileName)
            .use { return it.reader().readText() }
    }

    private fun getNearbyPlaces(): String = "nearby_places.json"

    fun getDistance(): String = "nearby_place_distance.json"

    fun getPlaceDetails(): String = "nearby_place_details.json"

}