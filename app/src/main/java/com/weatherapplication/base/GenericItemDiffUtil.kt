package com.weatherapplication.base

import com.learnig.android.mydata.data.models.Item

object GenericItemDiffUtil : ViewBindingDiffUtilCallback<com.learnig.android.mydata.data.models.Item>() {
    override fun areItemsTheSame(oldItem: com.learnig.android.mydata.data.models.Item, newItem: com.learnig.android.mydata.data.models.Item): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: com.learnig.android.mydata.data.models.Item, newItem: com.learnig.android.mydata.data.models.Item): Boolean = oldItem.equals(newItem)
}