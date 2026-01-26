package com.example.truckercore.core.config.flavor

import android.app.Activity
import com.example.truckercore.layers.presentation.login.view.fragments.welcome.WelcomeFragment
import com.example.truckercore.layers.presentation.login.view_model.welcome_fragment.helpers.WelcomePagerData

/**
 * Service layer responsible for exposing flavor-specific behavior
 * to the application.
 *
 * This class acts as a facade over [FlavorStrategy], delegating
 * navigation logic, UI configuration, and metadata access based
 * on the current application flavor.
 *
 * It helps decouple the rest of the app from direct strategy
 * implementations.
 */
class FlavorService(private val strategy: FlavorStrategy) {

    /**
     * Returns the application name for the current flavor.
     */
    val appName get() = strategy.getFlavor().appName

    /**
     * Returns the data used to populate the [WelcomeFragment] pager
     * for the current flavor.
     *
     * @return A list of [WelcomePagerData].
     */
    fun getWelcomeFragmentPagerData(): List<WelcomePagerData> =
        strategy.getWelcomePagerData()

    /**
     * Navigates the user to the check-in flow according to
     * the current flavor rules.
     *
     * @param current The current [Activity] context.
     */
    fun navigateToCheckIn(current: Activity) =
        strategy.navigateToCheckIn(current)

}