package com.weatherapplication.feature.searchcity


import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.weatherapplication.R
import com.weatherapplication.base.BaseFragment
import com.learnig.android.mydata.data.models.search.SearchItem
import com.weatherapplication.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException


//todo:  extension to class,sortList,empty state, other get list city,not network
@AndroidEntryPoint
class SearchCityFragment : BaseFragment<FragmentMainBinding, SearchCityViewModel>() {
    override val viewModelApp: SearchCityViewModel by viewModels()
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentMainBinding.inflate(inflater, container, false)

    private val adapter = SearchAdapter { item ->
        openWeatherFragment(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mockListCity()
        initView()
        observe()
        viewModelApp.showHistorySearch()
    }

    private fun mockListCity() {
        lifecycleScope.launch(Dispatchers.Default) {
            val jsonDataFromAsset = getJsonDataFromAsset(requireContext(), "pol_city.json")
            val list: List<com.learnig.android.mydata.data.models.search.SearchItem> = Gson().fromJson(
                jsonDataFromAsset, object : TypeToken<List<com.learnig.android.mydata.data.models.search.SearchItem?>?>() {}.type
            )
            withContext(Dispatchers.Main) {
                viewModelApp.listCity = list.sortedBy { it.name }
            }
        }

    }

    private fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

    private fun observe() {
        viewModelApp.city.observe(viewLifecycleOwner) {
            val list = it.first
            val isHistory = it.second
            setHistoryHead(isHistory && list.isNotEmpty())
            val oldHistory = adapter.isHistoryList
            adapter.setIsHistory(isHistory)
            adapter.apply {
                if (oldHistory != isHistory)
                    submitList(null)
                submitList(list)
            }

        }
    }

    private fun setHistoryHead(history: Boolean) {
        val visible = if (history) VISIBLE else GONE
        binding?.run {
            historyText.visibility = visible
            lineBottomHistoryText.visibility = visible
        }
    }

    private fun initView() {
        binding?.run {
            searchRecycler.adapter = adapter
            searchRecycler.layoutManager = LinearLayoutManager(context)
            searchCity.afterTextChanged {
                if (it.isNotEmpty()) {
                    viewModelApp.getFromCityList(it)
                } else {
                    viewModelApp.showHistorySearch()
                }
            }
        }

    }

    private fun openWeatherFragment(item: com.learnig.android.mydata.data.models.search.SearchItem) {
        val bundle = Bundle().apply {
            putSerializable("weatherId", item.id)
            putSerializable("cityName", item.name)
            putSerializable("lat", item.lat.toFloat())
            putSerializable("lon", item.lon.toFloat())
        }
        binding?.searchCity?.hideKeyboard()
        binding?.searchCity?.setText("")
        findNavController().navigate(R.id.action_searchCityFragment_to_weatherFragment, bundle)
    }

    companion object {
        fun newInstance(): SearchCityFragment {
            return SearchCityFragment()
        }
    }

}

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    })
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

