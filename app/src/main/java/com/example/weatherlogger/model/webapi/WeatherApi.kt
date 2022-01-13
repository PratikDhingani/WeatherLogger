package com.example.weatherlogger.model.webapi

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.jetbrains.annotations.TestOnly
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * WeatherApi is used to create ApiClent which will be used to fetch data from web api
 */
class WeatherApi {
    companion object {
        private const val API_URL = "https://api.openweathermap.org/data/2.5/"
        const val API_KEY = "f65a486aec0f31b8ddda6f5a4ff8a955"
        private var retrofit: Retrofit? = null
        private var timeout = 100L

        fun getApiClient(): Retrofit {
            val gson = GsonBuilder().setLenient().create()
            val okHttpClient = OkHttpClient.Builder()
                .readTimeout(timeout, TimeUnit.SECONDS)
                .connectTimeout(timeout, TimeUnit.SECONDS)
                .build()

            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(API_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
            }
            return retrofit!!
        }

        @TestOnly
        fun getUrl() : String {
            return WeatherApi.API_URL
        }
    }
}