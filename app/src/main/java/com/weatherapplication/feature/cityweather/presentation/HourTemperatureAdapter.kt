package com.weatherapplication.feature.cityweather.presentation

import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.weatherapplication.core.base.BaseAdapter
import com.weatherapplication.core.base.GenericItemDiffUtil
import com.weatherapplication.core.data.Item
import com.weatherapplication.databinding.ItemHourBinding
import com.weatherapplication.feature.cityweather.presentation.model.HourTemperatureDisplayable

class HourTemperatureAdapter() :
    BaseAdapter<Item, ItemHourBinding>(GenericItemDiffUtil) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewBindingViewHolder<Item, ItemHourBinding> {
        val inflater = parent.layoutInflater
        val binding = ItemHourBinding.inflate(inflater, parent, false)
        return HourTemperatureViewHolder(binding)
    }

    inner class HourTemperatureViewHolder(binding: ItemHourBinding) : ViewBindingViewHolder<Item, ItemHourBinding>(binding) {
        override fun bind(item: Item) {
            item as HourTemperatureDisplayable
            binding.run {
                hour.text = item.hour
                valueDetails.text = item.temperature.toString()
                Glide.with(itemView).load("https:" + item.weatherIcon).into(weatherIcon)
            }
        }
    }
}
