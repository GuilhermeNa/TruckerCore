package com.example.truckercore._utils.classes.abstractions

import com.example.truckercore._utils.classes.contracts.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class StateManager<T: UiState>(initialState: T) {

    private val _stateFlow: MutableStateFlow<T> = MutableStateFlow(initialState)
    val stateFlow get() = _stateFlow.asStateFlow()

    protected fun getStateValue(): T = _stateFlow.value

    protected fun setState(state: T) {
        _stateFlow.value = state
    }

}