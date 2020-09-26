package com.johnsoncontrol.nearbyplacesfinderapp.view.model
import java.io.Serializable
class PlaceDetailsResponse : Serializable {
    var address: String? = null
    var distanceInMiles: String? = null
    var distanceInKm: String? = null
    var name: String? = null
    var review: String? = null
}


