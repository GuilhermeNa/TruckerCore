package com.example.truckercore.layers.presentation.base.contracts

import com.example.truckercore.core.my_lib.expressions.get
import com.example.truckercore.layers.domain.use_case.authentication.HasLoggedUserUseCase

/**
 * Defines functionality for fragments that require validation of the user's authentication state.
 *
 * A `SecureFragment` provides access to a [HasLoggedUserUseCase], which determines
 * whether a user is currently authenticated. This allows the fragment to enforce
 * access restrictions and react appropriately when no logged-in user is found.
 *
 * Typical usage:
 * - Check whether a user session exists.
 * - If not, execute a recovery action (e.g., redirect to login or close the app).
 */
interface SecureFragment {

    /** Use case responsible for verifying whether a user is currently logged in. */
    val isUserLoggedUseCase: HasLoggedUserUseCase

    /**
     * Executes the provided [action] only if no authenticated user is found.
     *
     * This is typically used to enforce secure navigation flows or terminate
     * access to restricted areas when the session is invalid.
     *
     * Example:
     * ```
     * doIfUserNotFound {
     *     navigateToLoginScreen()
     * }
     * ```
     *
     * @param action Lambda executed when the user is not logged in.
     */
    fun doIfUserNotFound(action: () -> Unit) {
        if (!isUserLoggedUseCase().get()) action()
    }

}