package com.example.truckercore.view_model.welcome_fragment

sealed class ViewState {
    data object Enabled: ViewState()
    data object Disabled: ViewState()
    data object Invisible: ViewState()
    data object Gone: ViewState()
}