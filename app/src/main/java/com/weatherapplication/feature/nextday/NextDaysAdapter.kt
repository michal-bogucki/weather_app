package com.weatherapplication.feature.nextday

import android.view.ViewGroup

import com.bumptech.glide.Glide
import com.weatherapplication.base.BaseAdapter
import com.weatherapplication.base.GenericItemDiffUtil
import com.learnig.android.mydata.data.models.Item
import com.learnig.android.mydata.data.models.weather.database.NextDay
import com.weatherapplication.databinding.ItemNextDayWeatherBinding



class NextDaysAdapter() :
    BaseAdapter<com.learnig.android.mydata.data.models.Item, ItemNextDayWeatherBinding>(GenericItemDiffUtil) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewBindingViewHolder<com.learnig.android.mydata.data.models.Item, ItemNextDayWeatherBinding> {
        val inflater = parent.layoutInflater
        val binding = ItemNextDayWeatherBinding.inflate(inflater, parent, false)
        return NextDayViewHolder(binding)
    }


    inner class NextDayViewHolder(
        binding: ItemNextDayWeatherBinding
    ) :
        ViewBindingViewHolder<com.learnig.android.mydata.data.models.Item, ItemNextDayWeatherBinding>(binding) {

        override fun bind(item: com.learnig.android.mydata.data.models.Item) {
            item as com.learnig.android.mydata.data.models.weather.database.NextDay

            binding.nextDayDate.text = item.date
            binding.nextDayMaxTemperature.text = item.getMaxTempWithUnit()
            binding.nextDayMinTemperature.text = item.getMinTempWithUnit()
            Glide.with(itemView).load(item.getConditionIconUrl()).into(binding.nextDayIcon)
            Glide.with(itemView).load(item.getConditionIconUrl()).into(binding.nextDayIconBack)

        }
    }
}