package com.example.truckercore.view_model.view_models.welcome_fragment

import com.example.truckercore._utils.classes.ButtonState
import com.example.truckercore.view.ui_error.UiError

data class WelcomeUiState(
    val data: List<WelcomePagerData>,
    val pagerPos: Int = 0,
    val uiError: UiError.Critical? = null,
    val fabState: ButtonState = ButtonState(false)
) {

    fun isLastPage() = pagerPos == data.size

}