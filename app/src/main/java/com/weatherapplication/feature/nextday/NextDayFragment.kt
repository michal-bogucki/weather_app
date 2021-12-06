package com.weatherapplication.feature.nextday


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.weatherapplication.base.BaseFragment
import com.weatherapplication.databinding.FragmentNextDaysBinding
import com.weatherapplication.feature.DateFormat.formatterDate
import com.weatherapplication.feature.DateFormat.formatterTime
import dagger.hilt.android.AndroidEntryPoint
import java.time.format.DateTimeFormatter
import java.util.*


@AndroidEntryPoint
class NextDayFragment : BaseFragment<FragmentNextDaysBinding, NextDayViewModel>() {
    override val viewModelApp: NextDayViewModel by viewModels()
    private val args: NextDayFragmentArgs by navArgs()
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentNextDaysBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
        initView()
        viewModelApp.getWeather(args.weatherId)
    }

    private fun observe() {
        viewModelApp.weather.observe(viewLifecycleOwner) {
            binding?.run {
                cityName.text = it.city

                dateWeather.text = formatterDate.format(it.date)
                timeWeather.text = formatterTime.format(it.date)

            }
            adapter.submitList(it.nextDayWeather)
        }
    }

    private val adapter = NextDaysAdapter()

    private fun initView() {
        binding?.run {

            nextDaysRecycler.adapter = adapter
            nextDaysRecycler.layoutManager = LinearLayoutManager(requireContext())
        }

    }

    companion object {
        fun newInstance(): NextDayFragment {
            return NextDayFragment()
        }
    }

}
