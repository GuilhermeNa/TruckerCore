package com.example.truckercore.layers.presentation.viewmodels.view_models.welcome_fragment

import com.example.truckercore.core.classes.ButtonState
import com.example.truckercore.domain._shared.helpers.ViewError

data class WelcomeUiState(
    val pagerPos: Int = 0,
    val fabState: ButtonState = ButtonState(false),
    val uiError: ViewError.Critical? = null,
    val data: List<WelcomePagerData>
) : com.example.truckercore.presentation.viewmodels._shared._contracts.State {

    fun isLastPage() = pagerPos == (data.size - 1)

    override fun toString(): String {
        val fabString = if (fabState.isEnabled) "enabled" else "disabled"
        val uiErrorString = uiError?.let { "critical" }
        val dataString = data.size
        return "PagerPos ($pagerPos) | Fab ($fabString) | UiError ($uiErrorString) | DataSize ($dataString)"
    }
}