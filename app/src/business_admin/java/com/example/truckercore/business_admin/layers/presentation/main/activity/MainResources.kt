package com.example.truckercore.business_admin.layers.presentation.main.activity

import androidx.navigation.NavDestination
import com.example.truckercore.R

/**
 * MainResources
 *
 * Centralizes resource identifiers used by [MainActivity].
 *
 * Responsibilities:
 * - Defines top-level navigation destinations.
 * - Defines Toolbar options menu destinations.
 * - Provides helper methods to evaluate navigation state.
 *
 * Architectural Role:
 * - Keeps resource IDs isolated from Activity logic.
 * - Improves readability and maintainability.
 * - Prevents scattering navigation rules across the UI layer.
 */
class MainResources {

    /**
     * Defines all top-level destinations of the Drawer.
     * These destinations will:
     * - Show the hamburger icon instead of the Up arrow
     * - Close the drawer when selected
     */
    val topLevelDestination = setOf(
        R.id.nav_home,
        R.id.nav_business,
        R.id.nav_employees,
        R.id.nav_fleet,
        R.id.nav_fines,
        R.id.nav_settings
    )

    /**
     * Defines destinations that are triggered from the Toolbar Options Menu.
     *
     * These destinations:
     * - Are not part of the Drawer navigation.
     * - Are typically accessed through action items in the app bar.
     * - May require specific UI state handling (e.g., temporarily disabling menu items).
     */
    private val optionsMenuDestination = setOf(
        R.id.nav_messages,
        R.id.nav_profile
    )

    /**
     * Checks whether a given destination is a top-level destination.
     *
     * @param destination Current navigation destination
     * @return true if destination is top-level
     */
    fun isTopLevelDestination(destination: NavDestination): Boolean =
        topLevelDestination.contains(destination.id)

    /**
     * Determines whether the given NavDestination
     * was triggered from the Toolbar options menu.
     *
     * @param destination The current navigation destination.
     * @return true if the destination belongs to the options menu set.
     *
     * Usage:
     * - Allows differentiated handling between Drawer and Toolbar navigation.
     * - Can be used to manage menu visibility or UI state.
     */
    fun isMenuDestination(destination: NavDestination): Boolean =
        optionsMenuDestination.contains(destination.id)

}