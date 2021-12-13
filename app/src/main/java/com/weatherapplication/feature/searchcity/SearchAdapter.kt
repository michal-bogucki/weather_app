package com.weatherapplication.feature.searchcity

import android.view.ViewGroup
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.weatherapplication.R
import com.weatherapplication.base.BaseAdapter
import com.weatherapplication.base.GenericItemDiffUtil
import com.learnig.android.mydata.data.models.Item
import com.learnig.android.mydata.data.models.search.SearchItem
import com.learnig.android.mydata.data.remoteapi.State
import com.weatherapplication.databinding.ItemSearchBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class SearchAdapter(
    private val onItemClicked: (com.learnig.android.mydata.data.models.search.SearchItem) -> Unit
) :
    BaseAdapter<com.learnig.android.mydata.data.models.Item, ItemSearchBinding>(GenericItemDiffUtil) {
    var isHistoryList = true
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewBindingViewHolder<com.learnig.android.mydata.data.models.Item, ItemSearchBinding> {
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
        ViewBindingViewHolder<com.learnig.android.mydata.data.models.Item, ItemSearchBinding>(binding) {

        init {
            itemView.setOnClickListener {
                onItemClicked(
                    getItem(adapterPosition) as com.learnig.android.mydata.data.models.search.SearchItem
                )
            }

        }

        override fun bind(item: com.learnig.android.mydata.data.models.Item) {
            item as com.learnig.android.mydata.data.models.search.SearchItem
            if (isHistoryList)
                Glide.with(itemView).load(R.drawable.ic_round_history_24).into(binding.icon)
            else
                Glide.with(itemView).load(R.drawable.ic_round_location_on_24).into(binding.icon)
            binding.cityName.text = item.name
        }
    }
}