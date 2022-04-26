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
import com.weatherapplication.feature.cityweather.presentation.model.DataDisplayable
import com.weatherapplication.feature.cityweather.presentation.model.WeatherContract
import com.weatherapplication.feature.cityweather.presentation.model.WeatherDisplayable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherFragment :
    BaseFragment<FragmentMainBinding, WeatherContract.WeatherState, WeatherContract.WeatherEvent, WeatherViewModel>() {

    override val viewModel: WeatherViewModel by viewModels()
    private val args: WeatherFragmentArgs by navArgs()
    private val hourTemperatureAdapter by autoCleaned(initializer = { HourTemperatureAdapter() })
    private val dayAdapter by autoCleaned(initializer = { DayAdapter(::clickDate) })

    private fun clickDate(dataDisplayable: DataDisplayable) {
        viewModel.clickData(dataDisplayable)
    }

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
            dateRecyclerView.adapter = dayAdapter
            dateRecyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        }
    }

    override fun render(state: WeatherContract.WeatherState) {
        val error = state.error
        if (error != "") {
            showToast(error)
        }
        if (state.isLoading) binding.progressBar.visible() else binding.progressBar.gone()


        val weatherDisplayable = state.weatherDisplayable
        if (weatherDisplayable != null) {
            initWeather(weatherDisplayable)
        }

        val listDate = state.listDate
        if (listDate.isNotEmpty())
            dayAdapter.submitList(listDate)
    }

    private fun initWeather(weatherDisplayable: WeatherDisplayable) {
        binding.run {
            cityName.text = weatherDisplayable.cityName
            date.text = weatherDisplayable.date.toString()
            weather.text = weatherDisplayable.conditionWeatherName
            val s = "https:" + weatherDisplayable.weatherIcon
            Glide.with(requireContext()).load(s).into(weatherIcon)
            sunrise.titleDetails.text = "Sunrise"
            sunrise.valueDetails.text = weatherDisplayable.sunrise
            sunset.titleDetails.text = "Sunset"
            sunset.valueDetails.text = weatherDisplayable.sunset
            if (weatherDisplayable.temperature != Double.MAX_VALUE) {
                weatherTemperature.text = weatherDisplayable.temperature.toString()
                weatherTemperature.visible()
            } else
                weatherTemperature.gone()
            minTemperature.text = weatherDisplayable.minTemperature.toString()
            maxTemperature.text = weatherDisplayable.maxTemperature.toString()
            wind.titleDetails.text = "Wind now"
            wind.valueDetails.text = weatherDisplayable.windSpeed.toString()

            humidity.titleDetails.text = "Humidity"
            humidity.valueDetails.text = weatherDisplayable.humidity.toString()

            precipitation.titleDetails.text = "Precipitation"
            precipitation.valueDetails.text = weatherDisplayable.precipitation.toString()

            uvIndex.titleDetails.text = "UV Index"
            uvIndex.valueDetails.text = weatherDisplayable.uvIndex.toString()


            feelLike.titleDetails.text = "Feels like"
            if (weatherDisplayable.feelLike != Double.MAX_VALUE)
                feelLike.valueDetails.text = weatherDisplayable.feelLike.toString()
            else
                feelLike.valueDetails.text = "-"
            visibility.titleDetails.text = "Visibility"
            if (weatherDisplayable.visibility != Double.MAX_VALUE)
                visibility.valueDetails.text = weatherDisplayable.visibility.toString()
            else
                visibility.valueDetails.text = "-"
        }
        hourTemperatureAdapter.submitList(weatherDisplayable.listHourTemperature)

    }
}
