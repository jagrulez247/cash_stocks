package com.block.stocks.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> Fragment.observe(data: LiveData<T>, observer: Observer<in T>) {
    data.observe(viewLifecycleOwner, observer)
}