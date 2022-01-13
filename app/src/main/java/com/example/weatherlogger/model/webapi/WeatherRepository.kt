package com.example.weatherlogger.model.webapi

/**
 * WeatherRepository is used to get data from https://openweathermap.org
 * @param weatherService instance of WeatherService to get weather data
 * @param q is city name
 * @param appId is API key
 */
class WeatherRepository(val weatherService: WeatherService) {

    /*
     * getWeatherData will return weather data using lat,lon
     */
    fun getWeatherData(lat: String, lon: String, appId: String) =
        weatherService.getWeatherData(lat, lon, appId)
}