package com.example.weatherlogger.model.webapi.data

import com.google.gson.annotations.SerializedName

/**
 * WeatherCurrent data class is used to hold Weather Information
 */
data class WeatherCurrent(
    @SerializedName("temp") var temp : Double,
    @SerializedName("humidity") var humidiy : Int,
    @SerializedName("wind_speed") var windSpeed : Double,
    @SerializedName("weather") var weather: List<Weather>)
