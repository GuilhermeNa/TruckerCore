package com.example.truckercore.layers.presentation.nav_login.view_model.continue_register.effect

import com.example.truckercore.layers.presentation.nav_login.fragments.continue_register.ContinueRegisterFragment
import com.example.truckercore.layers.presentation.base.contracts.Effect

/**
 * Represents one-time effects that the [ContinueRegisterFragment] can trigger.
 *
 * Effects are not persisted in the state; instead, they are emitted to the UI layer
 * to perform actions such as navigation or showing temporary messages.
 */
sealed class ContinueRegisterFragmentEffect : Effect {

    /**
     * Navigation-related effects that instruct the UI to move
     * to another screen in the registration flow.
     */
    sealed class Navigation : ContinueRegisterFragmentEffect() {

        /**
         * Navigates to a screen informing the user that an internet connection
         * is required to proceed.
         */
        data object ToNoConnection : Navigation()

        /**
         * Navigates back to the Email Authentication screen,
         * typically used when the user chooses to start the registration process over.
         */
        data object ToEmailAuth : Navigation()

        /**
         * Navigates to the screen responsible for email verification,
         * used when the registration state indicates an unverified email.
         */
        data object ToVerifyEmail : Navigation()

        /**
         * Navigates to the screen where the user can complete their profile
         * by providing personal information such as username.
         */
        data object ToUserName : Navigation()

        /**
         * Navigates to a general notification or error screen,
         * used for unrecoverable or unexpected failures.
         */
        data object ToNotification : Navigation()
    }

    /**
     * Displays a temporary warning message to the user.
     *
     * This effect is used for non-blocking alerts and does not require navigation.
     *
     * @property message The message content to be shown in the toast.
     */
    data class WarningToast(val message: String) : ContinueRegisterFragmentEffect()

}