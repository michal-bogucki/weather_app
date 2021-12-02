package com.weatherapplication.base

import androidx.recyclerview.widget.DiffUtil
import com.weatherapplication.data.models.weather.database.Item

abstract class ViewBindingDiffUtilCallback<T : Item> :
    DiffUtil.ItemCallback<T>()