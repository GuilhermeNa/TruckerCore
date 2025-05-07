package com.example.truckercore.view_model.view_models.splash

import com.example.truckercore.model.configs.flavor.Flavor
import com.example.truckercore.model.errors.ErrorCode

/**
 * Represents one-time effects emitted by the Splash screen's ViewModel.
 *
 * These effects are typically used to trigger navigation actions that should happen only once,
 * such as moving to another screen or launching an activity.
 */
sealed class SplashEffect {

    /**
     * Indicates that the user is opening the app for the first time.
     * This should navigate to the Welcome screen.
     *
     * @param flavor The current app flavor used to customize the UI.
     */
    data class FirstTimeAccess(val flavor: Flavor) : SplashEffect()

    /**
     * Represents effects for users who have previously accessed the app.
     */
    sealed class AlreadyAccessed : SplashEffect() {

        /**
         * Indicates that the user must log in again.
         * Typically occurs when 'Keep me logged in' was not enabled.
         */
        data object RequireLogin : AlreadyAccessed()

        /**
         * Represents states for authenticated users.
         */
        sealed class AuthenticatedUser : AlreadyAccessed() {

            /**
             * Indicates that the user's registration is incomplete.
             * This should navigate to a continuation or onboarding fragment.
             */
            data object AwaitingRegistration : AuthenticatedUser()

            /**
             * Indicates that the user has completed their registration.
             * This should launch the main application screen.
             */
            data object RegistrationCompleted : AuthenticatedUser()
        }
    }

    /**
     * Represents an error that occurred during splash processing.
     *
     * @param error The domain-specific error code containing user-facing messages.
     */
    data class Error(val error: ErrorCode) : SplashEffect()

    //----------------------------------------------------------------------------------------------
    /**
     * Returns true if this effect should trigger navigation via the Navigation Component.
     */
    fun isFragmentNavigation() = this is FirstTimeAccess ||
            this is AlreadyAccessed.RequireLogin ||
            this is AlreadyAccessed.AuthenticatedUser.AwaitingRegistration

    /**
     * Returns true if this effect indicates that registration is complete
     * and the user should proceed to the main app flow.
     */
    fun isCompleteEffect() = this is AlreadyAccessed.AuthenticatedUser.RegistrationCompleted

}