package com.example.truckercore.view_model.view_models.welcome_fragment

import com.example.truckercore.view.ui_error.UiError

data class WelcomeUiState(
    val data: List<WelcomePagerData>,
    val pagerPos: Int = 0,
    val status: Status = Status.FirstPage
) {

    sealed class Status {
        data object FirstPage : Status()

        data object IntermediatePage : Status()

        data object LastPage : Status()

        data class Error(val uiError: UiError) : Status()
    }

}