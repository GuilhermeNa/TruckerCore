package com.example.truckercore.view_model.view_models.user_name

import com.example.truckercore.model.errors.AppException

/**
 * Represents one-time side effects triggered by the [UserNameViewModel].
 * These effects are used to navigate, show dialogs, or perform other non-state-driven UI updates.
 */
sealed class UserNameFragEffect {

    /**
     * Effect triggered when the user profile is successfully updated.
     * Typically used to navigate to the next screen.
     */
    data object ProfileUpdated : UserNameFragEffect()

    /**
     * Effect triggered when updating the user profile fails.
     *
     * @property error An [AppException] containing error details for UI handling.
     */
    data class ProfileUpdateFailed(val error: AppException) : UserNameFragEffect()

}