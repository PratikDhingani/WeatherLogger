package com.example.weatherlogger.model.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * WeatherData is used to define/create table for Weather database
 */
@Entity(tableName = "weatherdata")
class WeatherData(
    @ColumnInfo(name = "weathertemp") val weatherTemp: Double,
    @ColumnInfo(name = "humidity") val humidity: Int,
    @ColumnInfo(name = "windspeed") val windSpeed: Double,
    @ColumnInfo(name = "weatherid") val weatherId: Int,
    @ColumnInfo(name = "country") val country: String,
    @ColumnInfo(name = "city") val city: String,
    @ColumnInfo(name = "lat") val lat: Double,
    @ColumnInfo(name = "lon") val lon: Double,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "min") val min: Double,
    @ColumnInfo(name = "max") val max: Double,
    @ColumnInfo(name = "night") val night: Double,
    @ColumnInfo(name = "eve") val eve: Double,
    @ColumnInfo(name = "morn") val morn: Double,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "sunrise") val sunrise: String,
    @ColumnInfo(name = "sunset") val sunset: String
) {
    @PrimaryKey(autoGenerate = true)
    var uId: Int = 0
}