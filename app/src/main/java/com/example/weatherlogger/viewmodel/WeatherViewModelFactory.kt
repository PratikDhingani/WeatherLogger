package com.example.weatherlogger.viewmodel

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherlogger.model.webapi.WeatherApi
import com.example.weatherlogger.model.webapi.WeatherRepository
import com.example.weatherlogger.model.webapi.WeatherService

/**
 * WeatherViewModelFactory is used for create an instance of ViewModel with
 * its dependency
 */
class WeatherViewModelFactory (val application: Application) : ViewModelProvider.NewInstanceFactory() {

    var weatherDataViewModel: WeatherDataViewModel? = null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when (modelClass) {
            WeatherDataViewModel::class.java -> {
                if(weatherDataViewModel == null) {
                    val service = WeatherApi.getApiClient().create(WeatherService::class.java)
                    val repository = WeatherRepository(service)
                    weatherDataViewModel = WeatherDataViewModel(application, repository)
                    weatherDataViewModel as T
                } else {
                    weatherDataViewModel as T
                }
            }
            else -> {
                return super.create(modelClass)
            }
        }
    }
}