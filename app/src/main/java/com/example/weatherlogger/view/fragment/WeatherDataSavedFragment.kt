package com.example.weatherlogger.view.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherlogger.R
import com.example.weatherlogger.databinding.FragmentWeatherSavedDataBinding
import com.example.weatherlogger.view.adapter.WeatherAdapter
import com.example.weatherlogger.viewmodel.WeatherDataViewModel
import com.example.weatherlogger.viewmodel.WeatherViewModelFactory

/**
 * WeatherDataSavedFragment is used to display saved Weather Data
 */
class WeatherDataSavedFragment : Fragment() {

    private lateinit var weatherDataViewModel: WeatherDataViewModel
    private lateinit var binding: FragmentWeatherSavedDataBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_weather_saved_data, container, false
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

        initAdapter()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initAdapter() {
        val weatherAdapter = WeatherAdapter(requireContext())
        weatherDataViewModel.getDailyWeatherData()
        weatherDataViewModel.weatherDataList.observe(requireActivity(), {
            it?.let {
                if (it.isNotEmpty()) {
                    weatherAdapter.setWeatherData(it)
                    binding.recyclerData.layoutManager = LinearLayoutManager(context)
                    binding.recyclerData.adapter = weatherAdapter
                    binding.txtNoDataFound.visibility = View.GONE
                    binding.recyclerData.visibility = View.VISIBLE
                } else {
                    binding.txtNoDataFound.visibility = View.VISIBLE
                    binding.recyclerData.visibility = View.GONE
                }
            }

        })
    }
}