package com.example.truckercore.layers.presentation.base.abstractions.view.private

import android.os.Bundle
import com.example.truckercore.layers.domain.use_case.authentication.HasLoggedUserUseCase
import com.example.truckercore.layers.presentation.base.abstractions.view.base.BaseFragment
import com.example.truckercore.layers.presentation.base.contracts.SecureFragment
import org.koin.android.ext.android.inject

/**
 * Base fragment for screens that require an authenticated user.
 *
 * A `PrivateFragment` automatically validates the user's authentication
 * state during its lifecycle. If no logged-in user is found, the fragment
 * immediately redirects the user to an error screen.
 *
 * This class should be extended by any fragment that must be protected
 * behind authentication.
 */
abstract class PrivateFragment : BaseFragment(), SecureFragment {

    /**
     * Use case responsible for checking whether a user is currently logged in.
     * Injected via dependency injection.
     */
    override val isUserLoggedUseCase: HasLoggedUserUseCase by inject()

    /**
     * Called when the fragment is created.
     *
     * Performs an authentication check and redirects to an error activity
     * if no valid user session is found.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // If no authenticated user exists, navigate to the error screen
        doIfUserNotFound {
            navigateToErrorActivity(requireActivity())
        }
    }
}