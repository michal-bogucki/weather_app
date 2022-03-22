package com.weatherapplication.feature.main.presentation


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.weatherapplication.core.base.BaseFragment
import com.weatherapplication.databinding.FragmentMainBinding
import com.weatherapplication.feature.searchcity.presentation.model.SearchCityContract
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherFragment : BaseFragment<FragmentMainBinding,SearchCityContract.SearchCityState,SearchCityContract.SearchCityEvent, WeatherViewModel>() {

    override val viewModel: WeatherViewModel by viewModels()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentMainBinding.inflate(inflater, container, false)

    override fun initView() {

    }

    override fun render(state: SearchCityContract.SearchCityState) {


    }




}



