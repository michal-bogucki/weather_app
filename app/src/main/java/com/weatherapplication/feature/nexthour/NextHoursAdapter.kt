package com.weatherapplication.feature.nexthour

import android.view.ViewGroup

import com.bumptech.glide.Glide
import com.weatherapplication.base.BaseAdapter
import com.weatherapplication.base.GenericItemDiffUtil
import com.weatherapplication.data.models.weather.database.HourTemp
import com.weatherapplication.data.models.Item
import com.weatherapplication.databinding.ItemHoursWeatherBinding



class NextHoursAdapter() :
    BaseAdapter<Item, ItemHoursWeatherBinding>(GenericItemDiffUtil) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewBindingViewHolder<Item, ItemHoursWeatherBinding> {
        val inflater = parent.layoutInflater
        val binding = ItemHoursWeatherBinding.inflate(inflater, parent, false)
        return NextHoursViewHolder(binding)
    }


    inner class NextHoursViewHolder(
        binding: ItemHoursWeatherBinding
    ) :
        ViewBindingViewHolder<Item, ItemHoursWeatherBinding>(binding) {

        override fun bind(item: Item) {
            item as HourTemp

            binding.nextHourDate.text = item.time
            binding.nextHourTemperature.text = item.getTempWithUnit()
            Glide.with(itemView).load(item.getConditionIconUrl()).into(binding.nextHourIcon)
            Glide.with(itemView).load(item.getConditionIconUrl()).into(binding.nextHourIconBack)

        }
    }
}