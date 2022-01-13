package com.example.weatherlogger.view.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.weatherlogger.R
import com.example.weatherlogger.databinding.FragmentWeatherDataBinding
import com.example.weatherlogger.utils.Utils
import com.example.weatherlogger.viewmodel.WeatherDataViewModel
import com.example.weatherlogger.viewmodel.WeatherViewModelFactory

/**
 * WeatherDataFragment is used as main fragment, to display current Weather Data and
 * navigate to WeatherDataSavedFragment
 */
class WeatherDataFragment : Fragment() {

    private lateinit var weatherDataViewModel: WeatherDataViewModel
    private lateinit var binding: FragmentWeatherDataBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_weather_data, container, false
        )

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        weatherDataViewModel = ViewModelProvider(
            requireActivity(),
            WeatherViewModelFactory(requireActivity().application)
        ).get(WeatherDataViewModel::class.java)

        binding.viewModel = weatherDataViewModel
        binding.lifecycleOwner = this

        binding.mainAnimationView.apply {
            setAnimation(
                Utils.getWeatherBackground()
            )
            playAnimation()
        }

        weatherDataViewModel.weatherAnimation.observe(requireActivity(), {

            showWeatherView()

            binding.animationView.apply {
                setAnimation(
                    Utils.getWeatherAnimation(
                        it
                    )
                )
                playAnimation()
            }

            binding.animationViewSunrise.apply {
                setAnimation(Utils.getSunriseAnimation())
                playAnimation()
            }

            val slideDownAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_in_bottom)
            val slideLeftAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_out_left)
            binding.txtTemperature.startAnimation(slideDownAnimation)
            binding.txtHumidity.startAnimation(slideLeftAnimation)
            binding.txtWind.startAnimation(slideLeftAnimation)

        })

    }

    private fun showWeatherView() {
        binding.weatherView.visibility = View.VISIBLE
        binding.weatherViewMax.visibility = View.VISIBLE
        binding.weatherViewMin.visibility = View.VISIBLE
        binding.weatherViewMor.visibility = View.VISIBLE
        binding.weatherViewEve.visibility = View.VISIBLE
        binding.weatherViewNight.visibility = View.VISIBLE
        binding.btnViewData?.visibility = View.VISIBLE
        binding.btnSave?.visibility = View.VISIBLE
    }
}