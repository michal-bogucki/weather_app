package com.weatherapplication.feature.searchcity.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.weatherapplication.core.base.BaseFragment
import com.weatherapplication.core.extension.autoCleaned
import com.weatherapplication.core.extension.onTextChange
import com.weatherapplication.databinding.FragmentSearchBinding
import com.weatherapplication.feature.searchcity.presentation.model.SearchCityContract
import com.weatherapplication.feature.searchcity.presentation.model.SearchCityDisplayable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchCityFragment :
    BaseFragment<FragmentSearchBinding, SearchCityContract.SearchCityState, SearchCityContract.SearchCityEvent, SearchCityViewModel>() {

    override val viewModel: SearchCityViewModel by viewModels()
    private val searchAdapter by autoCleaned(initializer = { SearchAdapter(::onCityClicked) })

    private fun onCityClicked(searchCityDisplayable: SearchCityDisplayable) {
        binding.searchCity.text.clear()
        onEventSent(event = SearchCityContract.SearchCityEvent.ChooseCity(searchCityDisplayable))
        findNavController().navigate(SearchCityFragmentDirections.actionSearchCityFragmentToWeatherFragment(searchCityDisplayable.id))
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSearchBinding.inflate(inflater, container, false)

    override fun initView() {
        binding.run {
            searchRecycler.adapter = searchAdapter
            searchRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            onEventSent(event = SearchCityContract.SearchCityEvent.OnTextChange(searchCity.onTextChange()))
        }
    }

    override fun render(state: SearchCityContract.SearchCityState) {
        val actualSearchCityList = state.actualSearchCityList
        val historySearchCityList = state.historySearchCityList
        if (actualSearchCityList.isEmpty())
            searchAdapter.submitList(historySearchCityList)
        else
            searchAdapter.submitList(actualSearchCityList)

        val error = state.error
        if (error.isNotEmpty()) {
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
        }
    }
}
