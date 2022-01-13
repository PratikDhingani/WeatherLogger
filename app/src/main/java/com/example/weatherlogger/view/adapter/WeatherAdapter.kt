package com.example.weatherlogger.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.weatherlogger.R
import com.example.weatherlogger.model.database.WeatherData
import com.example.weatherlogger.utils.Utils
import java.util.*

/**
 * WeatherAdapter is used to display weather information
 * @param context is for reference of screen
 */
class WeatherAdapter(val context: Context) : RecyclerView.Adapter<WeatherAdapter.WeatherHolder>() {

    private var weatherDataList: List<WeatherData>? = null

    /**
     * set weather list to display in recyclerview
     */
    fun setWeatherData(dataList: List<WeatherData>) {
        weatherDataList = dataList
    }

    /**
     * Holder class for containing car information components
     * @param view is weather item layout
     */
    class WeatherHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtTemp: TextView? = null
        var txtDescription: TextView? = null
        var txtDate: TextView? = null
        var txtTempMax: TextView? = null
        var txtTempMin: TextView? = null
        var animationView: LottieAnimationView? = null

        init {
            txtTemp = itemView.findViewById(R.id.txtTemp)
            txtDescription = itemView.findViewById(R.id.txtDescription)
            txtDate = itemView.findViewById(R.id.txtDate)
            txtTempMax = itemView.findViewById(R.id.txtTempMax)
            txtTempMin = itemView.findViewById(R.id.txtTempMin)
            animationView = itemView.findViewById(R.id.animationView)
        }

        /**
         * to bind view with weather information
         */
        fun bindView(data: WeatherData) {
            txtTemp?.text = String.format(
                Locale.getDefault(),
                "%.0f°",
                data.weatherTemp
            )

            txtTempMax?.text = String.format(
                Locale.getDefault(),
                "%.0f°",
                data.max
            )

            txtTempMin?.text = String.format(
                Locale.getDefault(),
                "%.0f°",
                data.min
            )

            txtDescription?.text = data.description

            txtDate?.text = data.date

            animationView?.apply {
                setAnimation(
                    Utils.getWeatherAnimation(
                        data.weatherId
                    )
                )
                playAnimation()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherHolder {
        return WeatherHolder(
            LayoutInflater.from(context).inflate(
                R.layout.saved_data_raw,
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: WeatherHolder, position: Int) {
        val weatherData = weatherDataList?.get(position)
        weatherData?.let {
            holder.bindView(it)
        }
    }

    override fun getItemCount(): Int {
        return weatherDataList?.size ?: 0
    }
}