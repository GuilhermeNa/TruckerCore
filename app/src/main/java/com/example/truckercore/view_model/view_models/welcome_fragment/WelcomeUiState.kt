package com.example.truckercore.view_model.view_models.welcome_fragment

import com.example.truckercore._shared.classes.ButtonState
import com.example.truckercore.view_model._shared._contracts.State
import com.example.truckercore.view._shared.ui_error.UiError

data class WelcomeUiState(
    val pagerPos: Int = 0,
    val fabState: ButtonState = ButtonState(false),
    val uiError: UiError.Critical? = null,
    val data: List<WelcomePagerData>
) : State {

    fun isLastPage() = pagerPos == (data.size - 1)

    override fun toString(): String {
        val fabString = if (fabState.isEnabled) "enabled" else "disabled"
        val uiErrorString = uiError?.let { "critical" }
        val dataString = data.size
        return "PagerPos ($pagerPos) | Fab ($fabString) | UiError ($uiErrorString) | DataSize ($dataString)"
    }
}