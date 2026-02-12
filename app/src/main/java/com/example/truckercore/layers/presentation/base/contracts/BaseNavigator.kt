package com.example.truckercore.layers.presentation.base.contracts

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.clearFragmentResultListener
import androidx.fragment.app.setFragmentResultListener
import com.example.truckercore.NavGraphLoginDirections
import com.example.truckercore.R
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

    /**
     * Navigates to the global "No Connection" fragment from an [AppCompatActivity].
     *
     * This method provides a unified way to redirect the user to the
     * [NoConnectionFragment] when a network connection is lost in an
     * Activity-based context (i.e., outside of the Navigation Component flow).
     *
     * A [FragmentResultListener] is registered on the Activity's
     * [FragmentManager] to listen for the result emitted by the
     * [NoConnectionFragment]. When the connection is restored and a positive
     * result is received:
     *
     * - The listener is explicitly cleared to avoid multiple triggers.
     * - The provided [onRestored] callback is executed.
     *
     * @param activity The hosting [AppCompatActivity].
     * @param onRestored A lambda callback executed when the connection is restored.
     */
    fun navigateToNoConnection(
        activity: AppCompatActivity,
        onRestored: () -> Unit
    ) {
        val manager = activity.supportFragmentManager

        // Registers a one-shot listener for the result emitted by
        // NoConnectionFragment when connectivity is restored.
        manager.setFragmentResultListener(
            NoConnectionFragment.REQUEST_KEY,
            activity
        ) { _, bundle ->

            // Removes the listener to prevent multiple emissions or memory leaks.
            manager.clearFragmentResultListener(NoConnectionFragment.REQUEST_KEY)

            val connectionRestored =
                bundle.getBoolean(NoConnectionFragment.BUNDLE_KEY)

            if (connectionRestored) {
                onRestored()
            }
        }

        // Performs a manual fragment transaction to display
        // the NoConnectionFragment.
        //
        // The transaction replaces the current container content
        // and adds the operation to the back stack so the user
        // can navigate back if needed.
        manager.beginTransaction()
            .replace(R.id.act_check_in_frame, NoConnectionFragment())
            .addToBackStack(null)
            .commit()
    }

}