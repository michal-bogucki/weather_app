package com.weatherapplication.base

import androidx.recyclerview.widget.DiffUtil
import com.learnig.android.mydata.data.models.Item

abstract class ViewBindingDiffUtilCallback<T : com.learnig.android.mydata.data.models.Item> :
    DiffUtil.ItemCallback<T>()