package com.weatherapplication.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.learnig.android.mydata.data.models.Item


abstract class BaseAdapter<T : com.learnig.android.mydata.data.models.Item, VB : ViewBinding>(
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

    abstract class ViewBindingViewHolder<T : com.learnig.android.mydata.data.models.Item, out VB : ViewBinding>(
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

