package com.example.weatherlogger.model.webapi.data

import com.google.gson.annotations.SerializedName

/**
 * WeatherDaily data class is used to hold Weather Information
 */
data class WeatherDaily(
    @SerializedName("temp") var weatherTemp: WeatherTemp,
    @SerializedName("humidity") var humidiy: Int,
    @SerializedName("wind_speed") var windSpeed: Double,
    @SerializedName("weather") var weather: List<Weather>,
    @SerializedName("dt") var date: Long,
    @SerializedName("sunrise") var sunrise: Long,
    @SerializedName("sunset") var sunset: Long
)
