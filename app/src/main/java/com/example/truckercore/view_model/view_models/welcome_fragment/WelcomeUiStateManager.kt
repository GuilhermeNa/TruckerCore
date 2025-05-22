package com.example.truckercore.view_model.view_models.welcome_fragment

import com.example.truckercore._utils.classes.ButtonState
import kotlinx.coroutines.flow.MutableStateFlow

class WelcomeUiStateManager(pagerData: List<WelcomePagerData>) {

    private val _state: MutableStateFlow<WelcomeUiState> =
        MutableStateFlow(WelcomeUiState(data = pagerData))
    val state get() = _state
    val value get() = _state.value

    fun isLastPage() = value.isLastPage()

    fun pagerPos() = value.pagerPos

    fun updatePagerPosition(newPos: Int) {
        val newState = when (newPos) {
            FIRST_POS -> getStateForFirstPos()
            else -> getStateForPage(newPos)
        }
        _state.value = newState
    }

    private fun getStateForFirstPos(): WelcomeUiState {
        return value.copy(pagerPos = 0, fabState = ButtonState(false))
    }

    private fun getStateForPage(newPos: Int): WelcomeUiState {
        return value.copy(pagerPos = newPos, fabState = ButtonState(true))
    }

    companion object {
        private const val FIRST_POS = 0
    }

}