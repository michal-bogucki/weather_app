package com.weatherapplication.feature.cityweather.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.weatherapplication.core.base.BaseFragment
import com.weatherapplication.core.extension.autoCleaned
import com.weatherapplication.core.extension.gone
import com.weatherapplication.core.extension.visible
import com.weatherapplication.databinding.FragmentMainBinding
import com.weatherapplication.feature.cityweather.presentation.model.WeatherContract
import com.weatherapplication.feature.cityweather.presentation.model.WeatherDisplayable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherFragment :
    BaseFragment<FragmentMainBinding, WeatherContract.WeatherState, WeatherContract.WeatherEvent, WeatherViewModel>() {

    override val viewModel: WeatherViewModel by viewModels()
    private val args: WeatherFragmentArgs by navArgs()
    private val hourTemperatureAdapter by autoCleaned(initializer = { HourTemperatureAdapter() })

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentMainBinding.inflate(inflater, container, false)

    override fun initView() {
        viewModel.getWeather(args.cityId)
        binding.run {
            hourRecyclerView.adapter = hourTemperatureAdapter
            hourRecyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    override fun render(state: WeatherContract.WeatherState) {
        val error = state.error
        if (error != "") {
            showToast(error)
        }
        if (state.isLoading) binding.progressBar.visible() else binding.progressBar.gone()

        val weather = state.listWeatherData
        if (weather.isNotEmpty()) {
            initWeather(weather)
        }
    }

    private fun initWeather(weatherList: List<WeatherDisplayable>) {
        val weatherDisplayable = weatherList[0]
        binding.run {
            cityName.text = weatherDisplayable.cityName
            date.text = weatherDisplayable.date.toString()
            weather.text = weatherDisplayable.conditionWeatherName
            Glide.with(requireContext()).load(weatherDisplayable.weatherIcon).into(weatherIcon)
            sunrise.titleDetails.text = "Sunrise"
            sunrise.valueDetails.text = weatherDisplayable.sunrise
            sunset.titleDetails.text = "Sunset"
            sunset.valueDetails.text = weatherDisplayable.sunset
        }
    }
}
