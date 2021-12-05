package com.weatherapplication.feature.nextday


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.weatherapplication.base.BaseFragment
import com.weatherapplication.databinding.FragmentNextDaysBinding
import com.weatherapplication.databinding.FragmentNextHoursBinding
import com.weatherapplication.databinding.WeatherDetailsBinding

import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NextDayFragment : BaseFragment<FragmentNextDaysBinding, NextDayViewModel>() {
    override val viewModelApp: NextDayViewModel by viewModels()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentNextDaysBinding.inflate(inflater, container, false)


    companion object {
        fun newInstance(): NextDayFragment {
            return NextDayFragment()
        }
    }

}
