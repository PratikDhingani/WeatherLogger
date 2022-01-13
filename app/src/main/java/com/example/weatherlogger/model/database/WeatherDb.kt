package com.example.weatherlogger.model.database

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Abstrct class to create RoomDatabase
 */
@Database(entities = [WeatherData::class], version = 1)
abstract class WeatherDb : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}