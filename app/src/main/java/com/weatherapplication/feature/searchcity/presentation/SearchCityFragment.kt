package com.weatherapplication.feature.searchcity.presentation


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.weatherapplication.core.base.BaseFragment
import com.weatherapplication.databinding.FragmentMainBinding
import com.weatherapplication.feature.searchcity.presentation.model.SearchCityContract
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchCityFragment : BaseFragment<FragmentMainBinding,SearchCityContract.SearchCityState,SearchCityContract.SearchCityEvent, SearchCityViewModel>() {

    override val viewModel: SearchCityViewModel by viewModels()
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentMainBinding.inflate(inflater, container, false)

    override fun initView() {
        TODO("Not yet implemented")
    }

    override fun render(state: SearchCityContract.SearchCityState) {
        TODO("Not yet implemented")
    }

    override fun onEventSent(event: SearchCityContract.SearchCityEvent) {
        TODO("Not yet implemented")
    }



}



