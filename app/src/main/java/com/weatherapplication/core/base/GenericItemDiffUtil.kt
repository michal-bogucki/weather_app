package com.weatherapplication.core.base

import com.weatherapplication.core.data.Item

object GenericItemDiffUtil : ViewBindingDiffUtilCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean = oldItem == newItem
}