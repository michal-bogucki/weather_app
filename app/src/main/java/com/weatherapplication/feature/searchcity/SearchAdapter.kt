package com.weatherapplication.feature.searchcity

import android.view.ViewGroup
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.weatherapplication.R
import com.weatherapplication.base.BaseAdapter
import com.weatherapplication.base.GenericItemDiffUtil
import com.weatherapplication.data.models.Item
import com.weatherapplication.data.models.search.SearchItem
import com.weatherapplication.data.remoteapi.State
import com.weatherapplication.databinding.ItemSearchBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class SearchAdapter(
    private val onItemClicked: (SearchItem) -> Unit
) :
    BaseAdapter<Item, ItemSearchBinding>(GenericItemDiffUtil) {
    var isHistoryList = true
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewBindingViewHolder<Item, ItemSearchBinding> {
        val inflater = parent.layoutInflater
        val binding = ItemSearchBinding.inflate(inflater, parent, false)
        return SearchViewHolder(binding)
    }



    fun setIsHistory(isHistoryList:Boolean){
        this.isHistoryList = isHistoryList
    }


    inner class SearchViewHolder(
        binding: ItemSearchBinding,
    ) :
        ViewBindingViewHolder<Item, ItemSearchBinding>(binding) {

        init {
            itemView.setOnClickListener {
                onItemClicked(
                    getItem(adapterPosition) as SearchItem
                )
            }

        }

        override fun bind(item: Item) {
            item as SearchItem
            if (isHistoryList)
                Glide.with(itemView).load(R.drawable.ic_round_history_24).into(binding.icon)
            else
                Glide.with(itemView).load(R.drawable.ic_round_location_on_24).into(binding.icon)
            binding.cityName.text = item.name
        }
    }
}