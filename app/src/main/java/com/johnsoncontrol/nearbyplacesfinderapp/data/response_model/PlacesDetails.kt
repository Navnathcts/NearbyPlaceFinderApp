package com.johnsoncontrol.nearbyplacesfinderapp.data.response_model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class PlacesDetails : Serializable {
    @SerializedName("status")
    var status: String? = null

    @SerializedName("result")
    var result: result? = null

}

class result : Serializable {
    @SerializedName("formatted_address")
    var formatted_adress: String? = null

    @SerializedName("reviews")
    @Expose
    var reviews: List<Review>? = null
}

class Review : Serializable {
    @SerializedName("text")
    @Expose
    var text: String? = null
}