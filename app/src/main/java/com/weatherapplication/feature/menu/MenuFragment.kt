package com.weatherapplication.feature.menu


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.weatherapplication.R
import com.weatherapplication.base.BaseFragment
import com.weatherapplication.databinding.FragmentMenuBinding
import com.weatherapplication.feature.nexthour.NextHourFragmentArgs
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MenuFragment : BaseFragment<FragmentMenuBinding, MenuViewModel>() {
    override val viewModelApp: MenuViewModel by viewModels()
    private val args: NextHourFragmentArgs by navArgs()
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentMenuBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()

    }

    private fun initView() {
        binding?.run {

            backButton.setOnClickListener {
                findNavController().popBackStack()
            }
            nowButton.setOnClickListener {
                findNavController().popBackStack()
            }
            nextHoursButton.setOnClickListener {

                findNavController().navigate(R.id.action_menuFragment_to_nextHourFragment, setId())
            }
            nextDaysButton.setOnClickListener {

                findNavController().navigate(R.id.action_menuFragment_to_nextDayFragment, setId())
            }
        }
    }

    private fun setId(): Bundle {
        return Bundle().apply {
            putSerializable("weatherId", args.weatherId)
        }
    }

    companion object {
        fun newInstance(): MenuFragment {
            return MenuFragment()
        }
    }

}
