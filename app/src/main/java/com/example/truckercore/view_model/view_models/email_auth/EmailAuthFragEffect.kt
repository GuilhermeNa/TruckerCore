package com.example.truckercore.view_model.view_models.email_auth

import com.example.truckercore.model.errors.AppExceptionOld

/**
 * EmailAuthFragEffect defines one-time UI side effects triggered by the EmailAuthViewModel.
 * These effects represent actions that should be performed once, such as navigation or error notifications,
 * and are typically collected in the Fragment layer.
 */
sealed class EmailAuthFragEffect {

    /**
     * Effect triggered when a user account is successfully created.
     * This typically results in a navigation action within the UI.
     */
    data object UserCreated : EmailAuthFragEffect()

    /**
     * Effect triggered when user account creation fails.
     *
     * @property error The exception describing the failure reason, used to determine how the UI should respond.
     */
    data class UserCreationFailed(val error: AppExceptionOld) : EmailAuthFragEffect()

}