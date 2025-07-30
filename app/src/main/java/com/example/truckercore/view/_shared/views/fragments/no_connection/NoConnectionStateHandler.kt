package com.example.truckercore.view._shared.views.fragments.no_connection

import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.example.truckercore.databinding.FragmentNoConnectionBinding
import com.example.truckercore.view._shared._base.handlers.StateHandler

/**
 * State handler for NoConnectionFragment.
 * Manages UI transitions between idle and loading states.
 */
class NoConnectionStateHandler : StateHandler<FragmentNoConnectionBinding>() {

    /**
     * Sets the UI to its idle state.
     * - Shows the retry button.
     * - Hides the progress bar.
     * - Enables interaction.
     */
    fun idle() {
        enableButton(true)
        showButton(true)
        showProgressBar(false)
    }

    /**
     * Sets the UI to the loading state.
     * - Hides the retry button.
     * - Shows the progress bar.
     * - Disables further interaction.
     */
    fun checking() {
        setDelayedTransition()
        enableButton(false)
        showButton(false)
        showProgressBar(true)
    }

    /**
     * Animates the transition between UI states using AutoTransition.
     */
    private fun setDelayedTransition() {
        val viewGroup = getBinding().fragNoConnectionFrame
        TransitionManager.beginDelayedTransition(viewGroup, AutoTransition())
    }

    /**
     * Enables or disables the retry button.
     */
    private fun enableButton(enabled: Boolean) {
        getBinding().fragNoConnectionButton.isEnabled = enabled
    }

    /**
     * Shows or hides the retry button.
     */
    private fun showButton(show: Boolean) {
        val visibility = if (show) VISIBLE else INVISIBLE
        getBinding().fragNoConnectionButton.visibility = visibility
    }

    /**
     * Shows or hides the progress bar.
     */
    private fun showProgressBar(show: Boolean) {
        val visibility = if (show) VISIBLE else INVISIBLE
        getBinding().fragNoConnectionProgressBar.visibility = visibility
    }

}