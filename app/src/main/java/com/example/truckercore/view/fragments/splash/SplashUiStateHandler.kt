package com.example.truckercore.view.fragments.splash

import android.widget.TextView
import androidx.constraintlayout.motion.widget.MotionLayout
import com.example.truckercore.R
import com.example.truckercore.model.configs.flavor.Flavor

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

    /** ID of the second UI state in MotionLayout */
    val secondUiState = R.id.frag_verifying_email_state_2

    /** ID of the third UI state in MotionLayout */
    val thirdUiState = R.id.frag_verifying_email_state_3

    /**
     * Updates the splash text view with the app's flavor description.
     *
     * @param flavor The app flavor to be displayed.
     */
    fun bindAppName(flavor: Flavor) {
        textView.text = flavor.description
    }

    /**
     * Triggers the first visual transition to the second state (animated).
     */
    fun runFirstUiTransition() {
        motionLayout.transitionToState(secondUiState)
    }

    /**
     * Instantly jumps to the second UI state without animation.
     * Useful when restoring state after lifecycle changes.
     */
    fun jumpToSecondUiState() {
        motionLayout.jumpToState(secondUiState)
    }

    /**
     * Triggers the second visual transition.
     * Note: You are transitioning to the second state again.
     *       Consider renaming or adjusting if this is intentional.
     */
    fun runSecondUiTransition() {
        motionLayout.transitionToState(secondUiState)
    }

    /**
     * Instantly jumps to the third UI state.
     */
    fun jumpToThirdUiState() {
        motionLayout.jumpToState(thirdUiState)
    }

}

