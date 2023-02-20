package com.weatherapplication.feature.cityweather.presentation.newview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.weatherapplication.feature.cityweather.presentation.newview.components.weather.WeatherView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherNewFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val viewModel: WeatherNewViewModel by viewModels()
                WeatherView(viewModel = viewModel, navController = findNavController())
            }
        }
    }
}
