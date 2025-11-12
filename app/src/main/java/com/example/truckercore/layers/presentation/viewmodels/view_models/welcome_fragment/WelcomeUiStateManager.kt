package com.example.truckercore.layers.presentation.viewmodels.view_models.welcome_fragment

import com.example.truckercore.core.classes.ButtonState
import com.example.truckercore.layers.presentation.viewmodels.view_models.welcome_fragment.data.WelcomePagerData
import com.example.truckercore.layers.presentation.viewmodels.view_models.welcome_fragment.state.WelcomeFragmentState
import kotlinx.coroutines.flow.MutableStateFlow

class WelcomeUiStateManager(pagerData: List<WelcomePagerData>) {

    private val _state: MutableStateFlow<WelcomeFragmentState> =
        MutableStateFlow(WelcomeFragmentState(data = pagerData))
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

    private fun getStateForFirstPos(): WelcomeFragmentState {
        return value.copy(pagerPos = 0, fab = ButtonState(false))
    }

    private fun getStateForPage(newPos: Int): WelcomeFragmentState {
        return value.copy(pagerPos = newPos, fab = ButtonState(true))
    }

    companion object {
        private const val FIRST_POS = 0
    }

}