package com.example.weatherlogger

import com.example.weatherlogger.model.webapi.WeatherApi
import com.google.common.truth.Truth
import org.junit.Test

/**
 * WeatherApiTest is for test WeatherApi class
 */
class WeatherApiTest {
    @Test
    fun testApiClientNotNull() {
        Truth.assertThat(WeatherApi.getApiClient()).isNotNull()
    }

    @Test
    fun testApiUrlCheck() {
        Truth.assertThat(WeatherApi.getUrl()).isEqualTo("https://api.openweathermap.org/data/2.5/")
    }
}