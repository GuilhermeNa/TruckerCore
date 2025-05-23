package com.example.truckercore.view_model.view_models.email_auth.effect

import com.example.truckercore._utils.classes.contracts.Effect

/**
 * EmailAuthFragEffect defines one-time UI side effects triggered by the EmailAuthViewModel.
 * These effects represent actions that should be performed once, such as navigation or error notifications,
 * and are typically collected in the Fragment layer.
 */
sealed class EmailAuthEffect: Effect {

    data object ClearFocusAndHideKeyboard : EmailAuthEffect()

    data object NavigateToLogin : EmailAuthEffect()

    data class ShowErrorMessage(val message: String) : EmailAuthEffect()

}