package com.example.truckercore.layers.presentation.base.contracts

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.fragment.app.clearFragmentResultListener
import androidx.fragment.app.setFragmentResultListener
import com.example.truckercore.NavGraphLoginDirections
import com.example.truckercore.core.my_lib.expressions.navigateToDirection
import com.example.truckercore.layers.presentation.common.NoConnectionFragment
import com.example.truckercore.layers.presentation.common.NotificationActivity

/**
 * Defines the navigation contract for fragments and activities that require
 * transitioning to common destinations across the application.
 *
 * This interface centralizes reusable navigation behaviors such as:
 * - Navigating to the error/notification screen.
 * - Navigating to the "No Connection" screen and listening for
 *   connectivity restoration callbacks.
 *
 * Implementations of this interface are intended to be mixed into Fragments,
 * ViewModels, or other navigation coordinators to provide consistent navigation
 * patterns and avoid code duplication.
 */
interface BaseNavigator {

    /**
     * Navigates from the current [Activity] to the [NotificationActivity].
     *
     * This destination is typically used to display system-level errors,
     * application warnings, or critical notifications that are not tied
     * to a specific fragment flow.
     *
     * The method automatically finishes the current activity after launching
     * the new one to ensure a clean navigation stack.
     *
     * @param current The current [Activity] instance used to create the navigation context.
     */
    fun navigateToErrorActivity(current: Activity) {
        val intent = NotificationActivity.newInstance(context = current)
        current.startActivity(intent)
        current.finish()
    }

    /**
     * Navigates to the global "No Connection" fragment.
     *
     * This method provides a unified way to redirect the user to the
     * [NoConnectionFragment] when a network connection is lost.
     *
     * The caller can provide an [onRestored] callback, which will be invoked
     * when the connection has been successfully restored and the
     * [NoConnectionFragment] emits a positive result.
     *
     * The listener is automatically cleared after receiving the first result
     * to prevent multiple triggers or memory leaks.
     *
     * @param fragment The [Fragment] initiating the navigation and listening for the result.
     * @param onRestored A lambda callback executed when the connection is restored.
     */
    fun navigateToNoConnection(fragment: Fragment, onRestored: () -> Unit) {

        // Listener: waits for the NoConnectionFragment result emission.
        fragment.setFragmentResultListener(NoConnectionFragment.REQUEST_KEY) { _, bundle ->
            fragment.clearFragmentResultListener(NoConnectionFragment.REQUEST_KEY)
            val restored = bundle.getBoolean(NoConnectionFragment.BUNDLE_KEY)
            if (restored) onRestored()
        }

        // Navigation: triggers the global navigation to the NoConnectionFragment.
        val direction = NavGraphLoginDirections.actionGlobalNoConnectionFragmentNavLogin()
        fragment.navigateToDirection(direction)
    }

}