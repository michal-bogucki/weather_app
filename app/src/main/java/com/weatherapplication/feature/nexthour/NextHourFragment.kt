package com.weatherapplication.feature.nexthour


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.weatherapplication.base.BaseFragment
import com.weatherapplication.databinding.FragmentNextHoursBinding
import dagger.hilt.android.AndroidEntryPoint
import java.time.format.DateTimeFormatter


@AndroidEntryPoint
class NextHourFragment : BaseFragment<FragmentNextHoursBinding, NextHourViewModel>() {
    override val viewModelApp: NextHourViewModel by viewModels()
    private val args: NextHourFragmentArgs by navArgs()
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentNextHoursBinding.inflate(inflater, container, false)

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
                val formatterDate: DateTimeFormatter = DateTimeFormatter.ofPattern("EEEE, MMM dd")
                val formatterTime: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
                dateWeather.text = formatterDate.format(it.date)
                timeWeather.text = formatterTime.format(it.date)

            }
            adapter.submitList(it.temperatureList)
        }
    }

    private val adapter = NextHoursAdapter()

    private fun initView() {
        binding?.run {

            nextHourRecycler.adapter = adapter
            nextHourRecycler.layoutManager = LinearLayoutManager(requireContext())
        }

    }


    companion object {
        fun newInstance(): NextHourFragment {
            return NextHourFragment()
        }
    }

}
