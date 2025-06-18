package com.example.truckercore.view_model._shared._base.managers

import com.example.truckercore.view_model._shared._contracts.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class StateManagerII<T: State>(initialState: T) {

    private val _stateFlow: MutableStateFlow<T> = MutableStateFlow(initialState)
    val stateFlow get() = _stateFlow.asStateFlow()

    fun currentState(): T = _stateFlow.value

    fun update(state: T) {
        _stateFlow.value = state
    }

}