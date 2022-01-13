package com.example.weatherlogger.model.webapi

import com.example.weatherlogger.model.webapi.data.WeatherData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * WeatherService is used define Web Api
 */
interface WeatherService {

    /**
     * to get Weather information
     * @param lat latitude for current location
     * @param lon longitude for current location
     * @param appId api key
     * @param exclude to exclude data from web api reponse
     * @param units type of units for weather information
     */
    @GET("onecall")
    fun getWeatherData(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appId: String,
        @Query("exclude") exclude: String = "hourly",
        @Query("units") units: String = "metric"
    ): Call<WeatherData>
}