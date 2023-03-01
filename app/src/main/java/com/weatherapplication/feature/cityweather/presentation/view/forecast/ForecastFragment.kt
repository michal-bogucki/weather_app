package com.weatherapplication.feature.cityweather.presentation.view.forecast

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
import com.weatherapplication.core.activity.MainActivity
import com.weatherapplication.feature.cityweather.presentation.view.components.forecast.ForecastView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForecastFragment : Fragment() {
    val args: ForecastFragmentArgs by navArgs()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val viewModel: ForecastViewModel by viewModels()
                val cityId = (activity as MainActivity).cityId
                viewModel.getCity(cityId)
                ForecastView(viewModel = viewModel, navController = findNavController())
            }
        }
    }
}
