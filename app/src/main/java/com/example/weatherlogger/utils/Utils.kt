package com.example.weatherlogger.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.text.format.DateFormat
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.weatherlogger.R
import java.util.*

/**
 * Utils class is used for common operation for application
 */
class Utils {
    companion object {

        /**
         * to check network connection
         */
        @SuppressLint("NewApi")
        @RequiresApi(Build.VERSION_CODES.M)
        fun isNetworkConnected(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    return true
                } else return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
            }
            return false
        }

        const val NETWORK_UNAVAILABLE = "Check your Internet connectivity"

        /**
         * to toast a message
         */
        fun toast(context: Context, msg: String) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }

        /**
         * to choose animation based on weather code
         */
        fun getWeatherAnimation(weatherCode: Int): Int {
            if (weatherCode / 100 == 2) {
                return R.raw.storm_weather
            } else if (weatherCode / 100 == 3) {
                return R.raw.rainy_weather
            } else if (weatherCode / 100 == 5) {
                return R.raw.rainy_weather
            } else if (weatherCode / 100 == 6) {
                return R.raw.snow_weather
            } else if (weatherCode / 100 == 7) {
                return R.raw.unknown
            } else if (weatherCode == 800) {
                return R.raw.clear_day
            } else if (weatherCode == 801) {
                return R.raw.few_clouds
            } else if (weatherCode == 803) {
                return R.raw.broken_clouds
            } else if (weatherCode / 100 == 8) {
                return R.raw.cloudy_weather
            }
            return R.raw.unknown
        }

        /**
         * to choose background animation
         */
        fun getWeatherBackground(): Int {
            return R.raw.weather_background
        }

        /**
         * to choose sunrise animation
         */
        fun getSunriseAnimation(): Int {
            return R.raw.sunrise
        }

        /**
         * to get weather date format
         */
        fun getDateWithFormate(date: Date): String {
            return DateFormat.format("E, dd MMM yyyy HH:mm:ss", date).toString()
        }

        /**
         * to get sunrise/sunset time
         */
        fun getTimeWithFormate(date: Long): String {
            val cal = Calendar.getInstance(Locale.ENGLISH)
            cal.timeInMillis = date * 1000L
            return DateFormat.format("HH:MM", cal).toString()
        }
    }
}