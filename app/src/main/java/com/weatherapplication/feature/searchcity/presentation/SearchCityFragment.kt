package com.weatherapplication.feature.searchcity.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.*
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.weatherapplication.feature.searchcity.presentation.components.SearchView
import com.weatherapplication.feature.searchcity.presentation.model.SearchCityDisplayable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber

@AndroidEntryPoint
class SearchCityFragment : Fragment() {


    private fun onCityClicked(searchCityDisplayable: SearchCityDisplayable) {
        //viewModel.chooseCity(searchCityDisplayable)
        findNavController().navigate(SearchCityFragmentDirections.actionSearchCityFragmentToWeatherFragment(searchCityDisplayable.id))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val viewModel: SearchCityViewModel by viewModels()
                val value by viewModel.state.collectAsState()
                SearchView(value, viewModel, ::onCityClicked)
            }
        }
    }
}
//@SuppressLint("StateFlowValueCalledInComposition") // only used as initial value
//@Composable
//fun <T> rememberFlowWithLifecycle(
//    stateFlow: StateFlow<T>,
//    lifecycle: Lifecycle = LocalLifecycleOwner.current.lifecycle,
//    minActiveState: Lifecycle.State = Lifecycle.State.STARTED
//): State<T> = rememberFlowWithLifecycle(
//    flow = stateFlow,
//    lifecycle = lifecycle,
//    minActiveState = minActiveState
//).collectAsState(initial = stateFlow.value)
//
//@Composable
//fun <T> rememberFlowWithLifecycle(
//    flow: Flow<T>,
//    lifecycle: Lifecycle = LocalLifecycleOwner.current.lifecycle,
//    minActiveState: Lifecycle.State = Lifecycle.State.STARTED
//): Flow<T> = remember(flow, lifecycle) {
//    flow.flowWithLifecycle(
//        lifecycle = lifecycle,
//        minActiveState = minActiveState
//    )
//}