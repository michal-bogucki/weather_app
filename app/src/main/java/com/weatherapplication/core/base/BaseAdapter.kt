package com.weatherapplication.core.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.weatherapplication.core.data.Item


abstract class BaseAdapter<T : Item, VB : ViewBinding>(
    diffCallback: ViewBindingDiffUtilCallback<T>
) : ListAdapter<T, BaseAdapter.ViewBindingViewHolder<T, VB>>(diffCallback) {
    override fun onBindViewHolder(holder: ViewBindingViewHolder<T, VB>, position: Int) =
        holder.bind(item = getItem(position))

    override fun onBindViewHolder(
        holder: ViewBindingViewHolder<T, VB>,
        position: Int,
        payloads: MutableList<Any>
    ) {
        holder.bind(item = getItem(position), payloads = payloads)
    }


    protected val ViewGroup.layoutInflater: LayoutInflater
        get() = LayoutInflater.from(this.context)

    abstract class ViewBindingViewHolder<T : Item, out VB : ViewBinding>(
        protected val binding: VB
    ) : RecyclerView.ViewHolder(binding.root) {
        abstract fun bind(item: T)
        open fun bind(item: T, payloads: List<Any>) {
            if (payloads.isEmpty()) {
                bind(item = item)
            }
        }
    }
}

