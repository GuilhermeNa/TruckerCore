package com.example.truckercore.view_model.use_cases

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive

class CounterUseCase {

    private val _counter = MutableStateFlow(DEFAULT_TIMER)
    val counter get() = _counter.asStateFlow()

    suspend fun startCounter(initialValue: Int = DEFAULT_TIMER) = coroutineScope {
        for (value in initialValue downTo 0) {
            if(!isActive) return@coroutineScope
            _counter.value = value
            delay(1000)
        }
    }

    companion object {
        private const val DEFAULT_TIMER = 60
    }

}