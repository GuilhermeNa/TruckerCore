package com.example.truckercore.view_model.view_models.email_auth

/**
 * EmailAuthFragEffect defines one-time UI side effects triggered by the EmailAuthViewModel.
 * These effects represent actions that should be performed once, such as navigation or error notifications,
 * and are typically collected in the Fragment layer.
 */
sealed class EmailAuthEffect {

    data object ClearFocusAndHideKeyboard : EmailAuthEffect()

    data object NavigateToLogin : EmailAuthEffect()

    data class ShowErrorMessage(val message: String) : EmailAuthEffect()

}