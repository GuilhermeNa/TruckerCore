package com.example.truckercore.core.config.flavor

import android.app.Activity
import com.example.truckercore.layers.domain.model.access.Role
import com.example.truckercore.layers.presentation.login.view.fragments.welcome.WelcomeFragment
import com.example.truckercore.layers.presentation.login.view_model.welcome_fragment.helpers.WelcomePagerData

/**
 * Defines the contract for flavor-specific application behavior.
 *
 * Implementations of this interface encapsulate navigation flows,
 * role definition, UI configuration, and metadata based on the
 * active application flavor.
 *
 * This interface follows the Strategy pattern, allowing the
 * application behavior to vary without affecting consumers.
 */
interface FlavorStrategy {

    /**
     * Navigates the user to the check-in flow according to
     * the rules of the current flavor.
     *
     * @param current The current [Activity] context.
     */
    fun navigateToCheckIn(current: Activity)

    /**
     * Returns the primary [Role] associated with this flavor.
     *
     * @return The flavor role.
     */
    fun getRole(): Role

    /**
     * Returns the [Flavor] configuration for this strategy.
     *
     * @return The flavor metadata.
     */
    fun getFlavor(): Flavor

    /**
     * Returns the data used to populate the [WelcomeFragment] or onboarding
     * pager for this flavor.
     *
     * @return A list of [WelcomePagerData].
     */
    fun getWelcomePagerData(): List<WelcomePagerData>

}
