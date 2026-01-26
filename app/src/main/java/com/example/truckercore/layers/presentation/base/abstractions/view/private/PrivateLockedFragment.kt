package com.example.truckercore.layers.presentation.base.abstractions.view.private

import android.os.Bundle
import com.example.truckercore.layers.domain.use_case.authentication.HasLoggedUserUseCase
import com.example.truckercore.layers.presentation.base.abstractions.view.base.LockedFragment
import com.example.truckercore.layers.presentation.base.contracts.SecureFragment
import org.koin.android.ext.android.inject

/**
 * Base fragment for secure screens that also require exit confirmation.
 *
 * Combines:
 * - Authentication validation via [SecureFragment]
 * - Exit confirmation behavior via [LockedFragment]
 *
 * If no authenticated user is found, the user is redirected
 * to an error screen.
 */
abstract class PrivateLockedFragment : LockedFragment(), SecureFragment {

    /**
     * Use case responsible for checking whether a user is logged in.
     */
    override val isUserLoggedUseCase: HasLoggedUserUseCase by inject()

    /**
     * Validates user authentication during fragment creation.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        doIfUserNotFound {
            navigateToErrorActivity(requireActivity())
        }
    }
}