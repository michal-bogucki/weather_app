package com.weatherapplication.feature.cityweather.presentation

import android.view.ViewGroup
import com.weatherapplication.core.base.BaseAdapter
import com.weatherapplication.core.base.GenericItemDiffUtil
import com.weatherapplication.core.data.Item
import com.weatherapplication.databinding.ItemDayBinding
import com.weatherapplication.feature.cityweather.presentation.model.HourTemperatureDisplayable
import com.weatherapplication.feature.searchcity.presentation.model.SearchCityDisplayable

class DayAdapter(private val onItemClicked: (SearchCityDisplayable) -> Unit) :
    BaseAdapter<Item, ItemDayBinding>(GenericItemDiffUtil) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewBindingViewHolder<Item, ItemDayBinding> {
        val inflater = parent.layoutInflater
        val binding = ItemDayBinding.inflate(inflater, parent, false)
        return DayViewHolder(binding)
    }

    inner class DayViewHolder(
        binding: ItemDayBinding
    ) : ViewBindingViewHolder<Item, ItemDayBinding>(binding) {
        override fun bind(item: Item) {
            item as HourTemperatureDisplayable
            binding.run {
            }
        }
    }
}
