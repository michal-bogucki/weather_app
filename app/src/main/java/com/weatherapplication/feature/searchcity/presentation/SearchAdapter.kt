package com.weatherapplication.feature.searchcity.presentation

import android.annotation.SuppressLint
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.weatherapplication.R
import com.weatherapplication.core.base.BaseAdapter
import com.weatherapplication.core.base.GenericItemDiffUtil
import com.weatherapplication.core.data.Item
import com.weatherapplication.databinding.ItemSearchBinding
import com.weatherapplication.feature.searchcity.presentation.model.SearchCityDisplayable

class SearchAdapter(private val onItemClicked: (SearchCityDisplayable) -> Unit) : BaseAdapter<Item, ItemSearchBinding>(GenericItemDiffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewBindingViewHolder<Item, ItemSearchBinding> {
        val inflater = parent.layoutInflater
        val binding = ItemSearchBinding.inflate(inflater, parent, false)
        return SearchViewHolder(binding)
    }

    inner class SearchViewHolder(
        binding: ItemSearchBinding,
    ) : ViewBindingViewHolder<Item, ItemSearchBinding>(binding) {
        init {
            itemView.setOnClickListener {
                onItemClicked(getItem(absoluteAdapterPosition) as SearchCityDisplayable)
            }
        }

        @SuppressLint("SetTextI18n")
        override fun bind(item: Item) {
            item as SearchCityDisplayable
            binding.run {
                val image = if (item.isHistory) R.drawable.ic_round_history_24 else R.drawable.ic_round_location_on_24
                Glide.with(itemView).load(image).into(icon)
                cityName.text = item.cityName
                countryNamePostalCode.text = "${item.cityName},${item.countryName}"
            }
        }
    }
}
