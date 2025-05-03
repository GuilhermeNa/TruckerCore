package com.example.truckercore.view_model.view_models.continue_register

import com.example.truckercore.view.fragments.continue_register.ContinueRegisterDirection
import com.example.truckercore.view.fragments.continue_register.ContinueRegisterImageLevel

/**
 * UI model representing the data required to render the Continue Register screen.
 *
 * This model encapsulates:
 * - The user's email address.
 * - Whether the email is verified.
 * - Whether a user account associated with the email exists.
 *
 * It also provides computed properties used to:
 * - Determine the appropriate drawable level for status indicators.
 * - Decide the next navigation destination based on the registration state.
 *
 * @property email The user's email address (nullable).
 * @property verified Whether the user's email has been verified.
 * @property userExists Whether a user with this email exists in the system.
 */
data class ContinueRegisterUiModel(
    val email: String? = null,
    val verified: Boolean = false,
    val userExists: Boolean = false
) {

    /**
     * Returns the appropriate image level for the email verification status.
     */
    val expectedVerifiedImageLevel: ContinueRegisterImageLevel
        get() = if (verified) ContinueRegisterImageLevel.DONE
        else ContinueRegisterImageLevel.UNDONE

    /**
     * Returns the appropriate image level for the user existence status.
     */
    val expectedUserExistsImageLevel: ContinueRegisterImageLevel
        get() = if (userExists) ContinueRegisterImageLevel.DONE
        else ContinueRegisterImageLevel.UNDONE

    /**
     * Determines the expected navigation direction based on the current state.
     * If the email is not verified, the user must be sent to verification.
     * Otherwise, proceed to user creation.
     */
    val expectedNavigationDirection: ContinueRegisterDirection
        get() = if (!verified) ContinueRegisterDirection.VERIFY_EMAIL
        else ContinueRegisterDirection.CREATE_USER
}