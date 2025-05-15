package com.example.truckercore.model.infrastructure.integration.data.for_app.data.collections

import com.example.truckercore.model.infrastructure.integration.data.for_app.data.contracts.Filter

class SearchFilter(private val _dataSet: MutableSet<Filter> = mutableSetOf()) {

    val isEmpty get() = _dataSet.isEmpty()

     fun add(filter: Filter) {
         _dataSet.add(filter)
     }

    fun forEach(action: (Filter) -> Unit) {
        _dataSet.forEach { action(it) }
    }

}