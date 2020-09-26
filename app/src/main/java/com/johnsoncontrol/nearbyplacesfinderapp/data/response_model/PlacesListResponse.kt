package com.johnsoncontrol.nearbyplacesfinderapp.data.response_model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class PlacesListResponse : Serializable {
    @SerializedName("results")
    var customA: List<CustomA>? = null

    @SerializedName("status")
    var status: String? = null


}

class CustomA : Serializable {
    @SerializedName("geometry")
    var geometry: Geometry? = null

    @SerializedName("vicinity")
    var vicinity: String? = null

    @SerializedName("name")
    var name: String? = null

    @SerializedName("place_id")
    var place_id: String? = null
}


class Geometry : Serializable {
    @SerializedName("location")
    var locationA: LocationA? = null
}

class LocationA : Serializable {
    @SerializedName("lat")
    var lat = 0.0

    @SerializedName("lng")
    var lng = 0.0
}