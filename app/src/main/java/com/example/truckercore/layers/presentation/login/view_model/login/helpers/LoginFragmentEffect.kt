package com.example.truckercore.layers.presentation.login.view_model.login.helpers

import com.example.truckercore.core.my_lib.classes.EmailCredential
import com.example.truckercore.layers.presentation.base.contracts.Effect
import com.example.truckercore.layers.presentation.login.view.fragments.login.LoginFragment

/**
 * Defines one-time side effects emitted by the [LoginFragment].
 *
 * Unlike state— which is persistent and observable—effects represent
 * *transient events* that should be handled a single time, such as navigation
 * or launching background work.
 *
 * The sealed hierarchy allows exhaustive `when` expressions at compile time.
 */
sealed class LoginFragmentEffect : Effect {

    /**
     * Requests the system to start the login process with the provided credentials.
     *
     * @property credential User credentials required for authentication.
     */
    data class LaunchLoginTask(val credential: EmailCredential) : LoginFragmentEffect()

    /**
     * Requests an update to the user's preference settings (e.g., “Keep me logged in”).
     *
     * @property keepLogged Whether the session should persist across app launches.
     */
    data class UpdatePreferences(val keepLogged: Boolean) : LoginFragmentEffect()

    /**
     * Navigation-related effects, each representing a single navigation event.
     */
    sealed class Navigation : LoginFragmentEffect() {

        /** Navigate to the notifications screen. */
        data object ToNotification : Navigation()

        /** Navigate to the "No Connection" screen. */
        data object ToNoConnection : Navigation()

        /** Navigate to the check-in screen after successful login. */
        data object ToCheckIn : Navigation()

        /** Navigate to the password recovery screen. */
        data object ToForgetPassword : Navigation()

        /** Navigate to the new user registration screen. */
        data object ToNewUser : Navigation()
    }

}