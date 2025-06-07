package com.example.truckercore.view_model.view_models.email_auth.effect

import com.example.truckercore.view_model._shared._contracts.Effect

/**
 * EmailAuthFragEffect defines one-time UI side effects triggered by the EmailAuthViewModel.
 * These effects represent actions that should be performed once, such as navigation or error notifications,
 * and are typically collected in the Fragment layer.
 */
sealed class EmailAuthEffect: Effect {

    data object ClearFocusAndHideKeyboard : EmailAuthEffect()

    data class ShowToast(val message: String): EmailAuthEffect()

    data object NavigateToNotification: EmailAuthEffect()

    data object NavigateToLogin : EmailAuthEffect()

    data object NavigateToVerifyEmail: EmailAuthEffect()

}