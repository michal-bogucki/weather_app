package com.weatherapplication.feature.searchcity


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.weatherapplication.base.BaseFragment
import com.weatherapplication.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchCityFragment : BaseFragment<FragmentMainBinding, SearchCityViewModel>() {
    override val viewModelApp: SearchCityViewModel by viewModels()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentMainBinding.inflate(inflater, container, false)


    companion object {
        fun newInstance(): SearchCityFragment {
            return SearchCityFragment()
        }
    }

}
