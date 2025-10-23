package com.example.truckercore.layers.data.base.filter.collection

import com.example.truckercore.layers.data.base.filter.contract.Filter

class SearchFilters(private val _dataSet: MutableSet<Filter> = mutableSetOf()) {

    val isEmpty get() = _dataSet.isEmpty()

     fun add(filter: Filter) {
         _dataSet.add(filter)
     }

    fun forEach(action: (Filter) -> Unit) {
        _dataSet.forEach { action(it) }
    }

}