package com.example.weatherlogger.model.database

import android.content.Context
import androidx.room.Room

/**
 * WeatherDatabase is used to store Weather information
 */
object WeatherDatabase {
    /**
     * this method will return WeatherDatabase
     * @return will return WeatherDatabase
     */
    fun getDb(context: Context): WeatherDb {
        return Room.databaseBuilder(
            context,
            WeatherDb::class.java,
            "Weather-Database"
        ).build()
    }
}