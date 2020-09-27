package com.johnsoncontrol.nearbyplacesfinderapp.data.remote

import com.johnsoncontrol.nearbyplacesfinderapp.data.mock.MockInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    private var retrofit: Retrofit? = null
    val base_url = "https://maps.googleapis.com/maps/api/"
    val client: Retrofit?
        get() {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS).addInterceptor(interceptor)
                .addInterceptor(MockInterceptor())
                .build()
            retrofit = Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit
        }
    val apiService: APIInterface? = client?.create(
        APIInterface::class.java)
}