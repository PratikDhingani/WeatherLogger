package com.example.weatherlogger.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/**
 * WeatherDao class is to define operation for weather database
 */
@Dao
interface WeatherDao {

    /**
     * to insert WeatherData
     */
    @Insert
    suspend fun insertWeatherData(weatherData: WeatherData)

    /**
     * to fetch WeatherData list
     */
    @Query("select * from weatherdata ORDER BY uId DESC")
    suspend fun getWeatherData(): List<WeatherData>

    /**
     * to delete exist data in database
     */
    @Query("delete from weatherdata")
    suspend fun deleteWeatherData()
}