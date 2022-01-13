package com.example.weatherlogger.model.webapi.data

import com.google.gson.annotations.SerializedName

/**
 * Weather data class is used to hold Weather Information
 */
data class Weather(
    @SerializedName("id") var id : Int,
    @SerializedName("main") var main : String,
    @SerializedName("description") var description : String
    )
