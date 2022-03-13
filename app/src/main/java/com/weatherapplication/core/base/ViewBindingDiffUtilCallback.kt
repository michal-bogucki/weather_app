package com.weatherapplication.core.base

import androidx.recyclerview.widget.DiffUtil
import com.weatherapplication.core.data.Item

abstract class ViewBindingDiffUtilCallback<T : Item> :
    DiffUtil.ItemCallback<T>()