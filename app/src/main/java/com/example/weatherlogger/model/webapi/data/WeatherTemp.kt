package com.example.weatherlogger.model.webapi.data

import com.google.gson.annotations.SerializedName

/**
 * WeatherTemp data class is used to hold Weather Information
 */
data class WeatherTemp(
    @SerializedName("day") var day: Double,
    @SerializedName("min") var min: Double,
    @SerializedName("max") var max: Double,
    @SerializedName("night") var night: Double,
    @SerializedName("eve") var eve: Double,
    @SerializedName("morn") var morn: Double
)
