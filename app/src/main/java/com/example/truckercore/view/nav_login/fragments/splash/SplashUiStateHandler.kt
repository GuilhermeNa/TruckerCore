package com.example.truckercore.view.nav_login.fragments.splash

import android.widget.TextView
import androidx.constraintlayout.motion.widget.MotionLayout
import com.example.truckercore.R

/**
 * Responsible for handling MotionLayout transitions and binding UI elements
 * related to the splash screen's visual states.
 *
 * This class encapsulates all UI logic related to animations and text,
 * keeping the Fragment clean and focused on state handling.
 */
class SplashUiStateHandler(
    private val motionLayout: MotionLayout,
    private val textView: TextView
) {

    // Ui States
    val loadingUiState = R.id.frag_verifying_email_state_2
    val navigationUiState = R.id.frag_verifying_email_state_3

    /**
     * Updates the splash text view with the app's flavor description.
     *
     * @param flavor The app flavor to be displayed.
     */
    fun bindAppName(appName: String) {
        textView.text = appName
    }

    /**
     * Triggers the first visual transition to the second state (animated).
     */
    fun transitionToLoadingState() {
        motionLayout.transitionToState(loadingUiState)
    }

    /**
     * Instantly jumps to the second UI state without animation.
     * Useful when restoring state after lifecycle changes.
     */
    fun jumpToLoadingState() {
        motionLayout.jumpToState(loadingUiState)
    }

    /**
     * Triggers the second visual transition.
     * Note: You are transitioning to the second state again.
     *       Consider renaming or adjusting if this is intentional.
     */
    fun transitionToNavigationState() {
        motionLayout.transitionToState(navigationUiState)
    }

    /**
     * Instantly jumps to the third UI state.
     */
    fun jumpToNavigationState() {
        motionLayout.jumpToState(navigationUiState)
    }


}

