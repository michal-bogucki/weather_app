package com.weatherapplication.feature.weather


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.weatherapplication.R
import com.weatherapplication.base.BaseFragment
import com.weatherapplication.data.models.weather.database.WeatherModelLocal
import com.weatherapplication.data.remoteapi.State
import com.weatherapplication.databinding.FragmentWeatherDayBinding
import com.weatherapplication.feature.DateFormat.formatterDate
import com.weatherapplication.feature.DateFormat.formatterTime
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


//todo: empty and error state, no internet,landscape view

@AndroidEntryPoint
class WeatherFragment : BaseFragment<FragmentWeatherDayBinding, WeatherViewModel>() {
    override val viewModelApp: WeatherViewModel by viewModels()
    private val args: WeatherFragmentArgs by navArgs()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentWeatherDayBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
        viewModelApp.getWeather(args.weatherId,args.cityName,args.lat.toDouble(),args.lon.toDouble())

    }

    private fun observe() {
        viewModelApp.weather.observe(viewLifecycleOwner) { state ->
            when (state) {
                is State.Loading -> showLoading(true)
                is State.Success -> {
                    if (state.data != null) {
                        setViewElements(state.data)
                        showLoading(false)
                    }
                }
                is State.Error -> {
                    showToast(state.message)
                    showLoading(false)
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        when (isLoading) {
            true -> binding?.progressBar?.visibility = View.VISIBLE
            false -> binding?.progressBar?.visibility = View.GONE
        }
    }

    private fun setViewElements(weather: WeatherModelLocal) {
        viewModelApp.weatherId = weather.id
        binding?.run {
            menu.setOnClickListener {
                findNavController().navigate(R.id.action_weatherFragment_to_menuFragment, setId())
            }
            titleTimeWeather.visibility = VISIBLE
            cityName.visibility = VISIBLE
            cityName.text = weather.city
            countryName.text = weather.country
            Glide.with(this@WeatherFragment).load(weather.getConditionIconUrl())
                .into(iconWeatherBack)
            Glide.with(this@WeatherFragment).load(weather.getConditionIconUrl())
                .into(iconWeather)
            weatherCondition.text = weather.conditionText
            weatherTemp.text = weather.getTempWithUnit()
            weatherTemp.setTempColor(weather.temp)

            dateWeather.text = formatterDate.format(weather.date)
            timeWeather.text = formatterTime.format(weather.date)

            humidityDetails.titleDetails.text = getString(R.string.huminidity)
            humidityDetails.valueDetails.text = weather.getHumidityWithUnit()

            windDetails.titleDetails.text = getString(R.string.wind)
            windDetails.valueDetails.text = weather.getWindWithUnit()

            pressureDetails.titleDetails.text = getString(R.string.pressure)
            pressureDetails.valueDetails.text = weather.getPressureWithUnit()

            cloudDetails.titleDetails.text = getString(R.string.cloud)
            cloudDetails.valueDetails.text = weather.getCloudWithUnit()
        }
    }

    private fun setId(): Bundle {
        return Bundle().apply {
            putSerializable("weatherId", viewModelApp.weatherId)
        }
    }


    companion object {
        fun newInstance(): WeatherFragment {
            return WeatherFragment()
        }
    }


}

private fun TextView.setTempColor(temp: Double) {
    if (temp < 10) {
        this.setTextColor(AppCompatResources.getColorStateList(context, R.color.blue_temp))
    }
    if (temp >= 10 && temp < 20) {
        this.setTextColor(AppCompatResources.getColorStateList(context, R.color.black_temp))
    }
    if (temp >= 20) {
        this.setTextColor(AppCompatResources.getColorStateList(context, R.color.red_temp))
    }

}
