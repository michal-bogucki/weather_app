package com.weatherapplication.feature.weather.presentation


import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import com.weatherapplication.R
import dagger.hilt.android.AndroidEntryPoint


//todo: empty and error state, no internet,landscape view

@AndroidEntryPoint
class WeatherFragment : Fragment() {

//    override fun getViewBinding(
//        inflater: LayoutInflater,
//        container: ViewGroup?
//    ) = FragmentWeatherDayBinding.inflate(inflater, container, false)


    companion object {
        fun newInstance(): WeatherFragment {
            return WeatherFragment()
        }
    }


}