package com.weatherapplication.feature.searchcity.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.*
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.weatherapplication.feature.searchcity.presentation.components.SearchView
import com.weatherapplication.feature.searchcity.presentation.model.SearchCityDisplayable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchCityFragment : Fragment() {

    val viewModel: SearchCityViewModel by viewModels()
    private fun onCityClicked(searchCityDisplayable: SearchCityDisplayable) {
        viewModel.chooseCity(searchCityDisplayable)
        findNavController().navigate(SearchCityFragmentDirections.actionSearchCityFragmentToWeatherFragment(searchCityDisplayable.id))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val value by viewModel.state.collectAsState()
                SearchView(value, viewModel, ::onCityClicked)
            }
        }
    }
}
