package com.example.truckercore.layers.presentation.viewmodels._shared._base.managers

import com.example.truckercore.presentation.viewmodels._shared._contracts.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class StateManager<T: com.example.truckercore.presentation.viewmodels._shared._contracts.State>(initialState: T) {

    private val _stateFlow: MutableStateFlow<T> = MutableStateFlow(initialState)
    val stateFlow get() = _stateFlow.asStateFlow()

    protected fun getStateValue(): T = _stateFlow.value

    protected fun setState(state: T) {
        _stateFlow.value = state
    }

}