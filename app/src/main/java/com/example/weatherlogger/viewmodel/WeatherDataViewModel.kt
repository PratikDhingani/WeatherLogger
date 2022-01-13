package com.example.weatherlogger.viewmodel

import android.app.Application
import android.os.Build
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.example.weatherlogger.R
import com.example.weatherlogger.WeatherApplication
import com.example.weatherlogger.model.webapi.WeatherApi
import com.example.weatherlogger.model.webapi.WeatherRepository
import com.example.weatherlogger.model.webapi.data.WeatherCurrent
import com.example.weatherlogger.model.webapi.data.WeatherDaily
import com.example.weatherlogger.model.webapi.data.WeatherData
import com.example.weatherlogger.utils.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

/**
 * WeatherDataViewModel is used to get data from model and serve to view
 * @param application application instance
 * @param weatherRepository repo instance to fetch data from repository
 */
@RequiresApi(Build.VERSION_CODES.M)
class WeatherDataViewModel(
    val app: Application,
    private val weatherRepository: WeatherRepository
) : AndroidViewModel(app) {

    private val TAG = WeatherDataViewModel::class.java.canonicalName
    private var weatherDaily: WeatherDaily? = null
    private var weatherCurrent: WeatherCurrent? = null

    /**
     * to hold & update latitude
     */
    var lat: Double = 0.0

    /**
     * to hold & update longitude
     */
    var lon: Double = 0.0

    /**
     * to hold & update weather data
     */
    var weatherDataList =
        MutableLiveData<List<com.example.weatherlogger.model.database.WeatherData>?>()

    /**
     * to hold & update temperature information
     */
    val temperature = MutableLiveData<String>()

    /**
     * to hold & update max temperature information
     */
    val temperatureMax = MutableLiveData<String>()

    /**
     * to hold & update min temperature information
     */
    val temperatureMin = MutableLiveData<String>()

    /**
     * to hold & update morning temperature information
     */
    val temperatureMorning = MutableLiveData<String>()

    /**
     * to hold & update evening temperature information
     */
    val temperatureEvening = MutableLiveData<String>()

    /**
     * to hold & update night temperature information
     */
    val temperatureNight = MutableLiveData<String>()

    /**
     * to hold & update humidity information
     */
    val humidity = MutableLiveData<String>()

    /**
     * to hold & update cloud information
     */
    val cloudDescription = MutableLiveData<String>()

    /**
     * to hold & update wind information
     */
    val wind = MutableLiveData<String>()

    /**
     * to animate changes properties
     */
    val weatherAnimation = MutableLiveData<Int>()

    /**
     * to hold & update sunrise information
     */
    val sunrise = MutableLiveData<String>()

    /**
     * to hold & update sunset information
     */
    val sunset = MutableLiveData<String>()

    /**
     * to get lat,lon
     */
    private val updateLatLon = MutableLiveData<Boolean>()

    init {
        getLocalSavedData()
    }

    /**
     * get weather data/information from Web Api
     */
    fun getWeatherData() {
        if (lat == 0.0 && lon == 0.0) {
            updateLatLon.value = true
            return
        }

        if (Utils.isNetworkConnected(getApplication())) {
            Log.d(TAG, "Getting weather data ..")
            updateLatLon.value = false
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    weatherRepository
                        .getWeatherData(lat.toString(), lon.toString(), WeatherApi.API_KEY)
                        .enqueue(WeatherCallBack())
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) { onError(e.message.toString()) }
                }
            }
        } else {
            Utils.toast(app, Utils.NETWORK_UNAVAILABLE)
        }
    }

    private fun onError(message: String) {
        Utils.toast(getApplication(), Utils.NETWORK_UNAVAILABLE)
        Log.e(TAG, message)
    }

    inner class WeatherCallBack : Callback<WeatherData> {
        override fun onResponse(call: Call<WeatherData>, response: Response<WeatherData>) {
            Log.d(TAG, "Data fetched successfully")

            val weatherData = response.body()

            Log.d(TAG, weatherData.toString())
            if (weatherData == null) {
                Utils.toast(app, "No Data Found")
            } else {
                weatherCurrent = weatherData.weatherCurrent
                weatherDaily = weatherData.weatherDaily[0]
                showWeatherData(weatherDaily, weatherCurrent)
            }
        }

        override fun onFailure(call: Call<WeatherData>, t: Throwable) {
            Log.e(TAG, t.message.toString())
        }

    }

    private fun insertWeatherData() {
        if (weatherCurrent == null || weatherDaily == null) {
            Utils.toast(app, "No Data Found")
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            val weatherApp = app as WeatherApplication
            val dailyWeatherData = com.example.weatherlogger.model.database.WeatherData(
                weatherCurrent!!.temp,
                weatherCurrent!!.humidiy,
                weatherCurrent!!.windSpeed,
                weatherCurrent!!.weather[0].id,
                "",
                "",
                lat,
                lon,
                weatherCurrent!!.weather.get(0).description,
                weatherDaily!!.weatherTemp.min,
                weatherDaily!!.weatherTemp.max,
                weatherDaily!!.weatherTemp.night,
                weatherDaily!!.weatherTemp.eve,
                weatherDaily!!.weatherTemp.morn,
                Utils.getDateWithFormate(Date()),
                Utils.getTimeWithFormate(weatherDaily!!.sunrise),
                Utils.getTimeWithFormate(weatherDaily!!.sunset)
            )
            weatherApp.repository.insertWeatherData(dailyWeatherData)
        }
    }

    /**
     * to display weather data
     */
    fun showWeatherData(weatherDaily: WeatherDaily?, weatherCurrent: WeatherCurrent?) {
        if (weatherDaily == null || weatherCurrent == null) {
            return
        }

        cloudDescription.value = weatherCurrent.weather[0].description

        temperature.value = String.format(
            Locale.getDefault(),
            "%.0f°",
            weatherCurrent.temp
        )
        temperatureMax.value = String.format(
            Locale.getDefault(),
            "%.0f°",
            weatherDaily.weatherTemp.max
        )

        temperatureMin.value = String.format(
            Locale.getDefault(),
            "%.0f°",
            weatherDaily.weatherTemp.min
        )

        temperatureMorning.value = String.format(
            Locale.getDefault(),
            "%.0f°",
            weatherDaily.weatherTemp.morn
        )

        temperatureEvening.value = String.format(
            Locale.getDefault(),
            "%.0f°",
            weatherDaily.weatherTemp.eve
        )

        temperatureNight.value = String.format(
            Locale.getDefault(),
            "%.0f°",
            weatherDaily.weatherTemp.night
        )

        humidity.value = String.format(
            Locale.getDefault(),
            "%d%%",
            weatherCurrent.humidiy
        )
        wind.value = String.format(
            Locale.getDefault(),
            "%.0f km/hr",
            weatherCurrent.windSpeed
        )

        sunrise.value = Utils.getTimeWithFormate(weatherDaily.sunrise)

        sunset.value = Utils.getTimeWithFormate(weatherDaily.sunset)

        weatherAnimation.value = weatherCurrent.weather[0].id

    }

    /**
     * Navigate back to WeatherDataViewModel to WeatherDataSavedFragment
     */
    fun gotoNextDaysFragment(view: View) {
        view.findNavController()
            .navigate(R.id.action_weatherDataFragment_to_weatherDataDailyFragment)
    }

    /**
     * to get Weather Data from Api and Saved in local database
     */
    fun saveData() {
        getWeatherData()
        insertWeatherData()
        getLocalSavedData()
        Utils.toast(app, "Data Saved Successfully")
    }

    /**
     * to get Saved data from database
     */
    private fun getLocalSavedData() {
        viewModelScope.launch(Dispatchers.IO) {
            weatherDataList.postValue((app as WeatherApplication).repository.getWeatherData())
        }
    }

    /**
     * Navigate back to WeatherDataSavedFragment to WeatherDataViewModel
     */
    fun gotoTodaysFragment(view: View) {
        view.findNavController().navigateUp()
    }

    /**
     * to get Saved data from database
     */
    fun getDailyWeatherData() {
        getLocalSavedData()
    }

}