package com.johnsoncontrol.nearbyplacesfinderapp.data.response_model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

class DistanceResponse:Serializable {
    @SerializedName("status")
    var status: String? = null

    @SerializedName("rows")
    var rows: ArrayList<InfoDistance>? = null

}

class ValueItem : Serializable {
    @SerializedName("value")
    var value: Long = 0

    @SerializedName("text")
    var text: String? = null
}

class DistanceElement : Serializable {
    @SerializedName("status")
    var status: String? = null

    @SerializedName("duration")
    var duration: ValueItem? = null

    @SerializedName("distance")
    var distance: ValueItem? = null
}

class InfoDistance : Serializable{
    @SerializedName("elements")
    var elements: ArrayList<DistanceElement>? = null


}