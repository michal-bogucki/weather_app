package com.weatherapplication.core.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.weatherapplication.core.data.ViewEvent
import com.weatherapplication.core.data.ViewState
import com.weatherapplication.core.extension.autoCleaned
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

abstract class BaseFragment<VB : ViewBinding, STATE : ViewState, EVENT : ViewEvent, VM : BaseViewModel<STATE, EVENT>> : Fragment() {

    private var _binding: VB by autoCleaned()
    val binding: VB get() = _binding

    protected abstract val viewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getViewBinding(inflater, container)
        return binding.root
    }

    abstract fun initView()
    abstract fun render(state: STATE)
    fun onEventSent(event: EVENT) = viewModel.setEvent(event)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeState()
    }

    private fun observeState() {
        viewModel.state
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> render(state) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    fun applicationContext(): Context = requireActivity().applicationContext

    fun showToast(message: String) = Toast.makeText(context, message, Toast.LENGTH_LONG).show()

    protected abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB
}
