package com.weatherapplication.feature.cityweather.presentation.view.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.weatherapplication.feature.cityweather.presentation.view.components.weather.WeatherView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherNewFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val args: WeatherNewFragmentArgs by navArgs()
                val id = args.cityId
                val viewModel: WeatherNewViewModel by viewModels()
                WeatherView(viewModel = viewModel, findNavController())
            }
        }
    }
}
