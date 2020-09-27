package com.johnsoncontrol.nearbyplacesfinderapp.utils

import android.provider.MediaStore
import android.view.View
import android.widget.RadioButton
import android.widget.TextView

fun View.showView() {
    this.visibility = View.VISIBLE
}

fun View.hideView() {
    this.visibility = View.GONE
}
fun RadioButton.showSelected(){
    this.isChecked = true
}

fun TextView.showErrorMessage(errorMessage:String){
    this.showView()
    this.text = errorMessage
}