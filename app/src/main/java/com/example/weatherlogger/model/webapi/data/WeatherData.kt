package com.example.weatherlogger.model.webapi.data

import com.google.gson.annotations.SerializedName

/**
 * WeatherData data class is used to hold Weather Information
 */
data class WeatherData(
    @SerializedName("current") var weatherCurrent: WeatherCurrent,
    @SerializedName("daily") var weatherDaily: List<WeatherDaily>
)
