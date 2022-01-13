package com.example.weatherlogger.model.database

/**
 * WeatherRepository is used to interact between WeatherDatabase and Model
 */
class WeatherRepository(private val weatherDao: WeatherDao) {

    suspend fun getWeatherData(): List<WeatherData> {
        return weatherDao.getWeatherData()
    }

    suspend fun insertWeatherData(data: WeatherData) {
        weatherDao.insertWeatherData(data)
    }

    suspend fun deleteWeatherData() {
        weatherDao.deleteWeatherData()
    }

}