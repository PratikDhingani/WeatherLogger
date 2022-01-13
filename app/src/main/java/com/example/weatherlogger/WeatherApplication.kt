package com.example.weatherlogger

import android.app.Application
import com.example.weatherlogger.model.database.WeatherDatabase
import com.example.weatherlogger.model.database.WeatherRepository

/**
 * Application class to create database and repository instance
 */
class WeatherApplication : Application() {

    val database by lazy {
        WeatherDatabase.getDb(applicationContext)
    }

    val repository by lazy {
        WeatherRepository(database.weatherDao())
    }
}