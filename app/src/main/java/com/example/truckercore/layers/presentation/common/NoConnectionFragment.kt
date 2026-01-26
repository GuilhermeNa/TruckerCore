package com.example.truckercore.layers.presentation.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.example.truckercore.core.my_lib.expressions.isInternetAvailable
import com.example.truckercore.core.my_lib.expressions.launchAndRepeatOnFragmentStartedLifeCycle
import com.example.truckercore.core.my_lib.expressions.popBackstack
import com.example.truckercore.core.my_lib.expressions.showToast
import com.example.truckercore.databinding.FragmentNoConnectionBinding
import com.example.truckercore.layers.presentation.base.abstractions.view.base.LockedFragment
import com.example.truckercore.layers.presentation.base.handlers.StateHandler
import kotlinx.coroutines.delay

/**
 * Fragment that handles cases where the device is offline.
 * It provides the user an option to retry connecting to the internet.
 * On successful reconnection, the fragment returns a result to the calling fragment.
 */
class NoConnectionFragment : LockedFragment() {

    // ViewBinding instance for accessing layout views safely.
    private var _binding: FragmentNoConnectionBinding? = null
    private val binding get() = _binding!!

    // Handles UI transitions and state management.
    private val stateHandler by lazy { NoConnectionStateHandler() }

    //----------------------------------------------------------------------------------------------
    // Lifecycle: onCreateView
    //----------------------------------------------------------------------------------------------

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoConnectionBinding.inflate(inflater)
        stateHandler.initialize(binding) // Setup binding for state handler
        return binding.root
    }

    //----------------------------------------------------------------------------------------------
    // Lifecycle: onViewCreated
    //----------------------------------------------------------------------------------------------

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setButtonClickListener()
    }

    /**
     * Sets up click listener for the retry button.
     * Disables the button during the connection check, and handles the result accordingly.
     */
    private fun setButtonClickListener() {
        binding.fragNoConnectionButton.setOnClickListener {
            stateHandler.checking() // Switch to loading state

            // Launch coroutine tied to fragment lifecycle
            launchAndRepeatOnFragmentStartedLifeCycle {
                delay(ANIMATION_DELAY)

                if (requireContext().isInternetAvailable()) {
                    closeFragmentAndNotifyListener()
                } else showSnackAndReturnToIdleState()
            }
        }
    }

    /**
     * Called when internet is reconnected.
     * Pops this fragment and returns a result to the caller.
     */
    private fun closeFragmentAndNotifyListener() {
        setFragmentResult(REQUEST_KEY, bundleOf(BUNDLE_KEY to true))
        popBackstack()
    }

    /**
     * Called when reconnection fails.
     * Displays feedback to the user and resets the UI state.
     */
    private fun showSnackAndReturnToIdleState() {
        showToast(TOAST_MESSAGE)
        stateHandler.idle()
    }

    //----------------------------------------------------------------------------------------------
    // Lifecycle: onDestroyView
    //----------------------------------------------------------------------------------------------

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //----------------------------------------------------------------------------------------------
    // Companion Object
    //----------------------------------------------------------------------------------------------

    companion object {
        private const val ANIMATION_DELAY = 3000L
        private const val TOAST_MESSAGE = "Falha na conexão"
        const val REQUEST_KEY = "no_connection_result"
        const val BUNDLE_KEY = "restored"
    }

}

/**
 * State handler for NoConnectionFragment.
 * Manages UI transitions between idle and loading states.
 */
private class NoConnectionStateHandler : StateHandler<FragmentNoConnectionBinding>() {

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
        val viewGroup = binding.fragNoConnectionFrame
        TransitionManager.beginDelayedTransition(viewGroup, AutoTransition())
    }

    /**
     * Enables or disables the retry button.
     */
    private fun enableButton(enabled: Boolean) {
        binding.fragNoConnectionButton.isEnabled = enabled
    }

    /**
     * Shows or hides the retry button.
     */
    private fun showButton(show: Boolean) {
        val visibility = if (show) VISIBLE else INVISIBLE
        binding.fragNoConnectionButton.visibility = visibility
    }

    /**
     * Shows or hides the progress bar.
     */
    private fun showProgressBar(show: Boolean) {
        val visibility = if (show) VISIBLE else INVISIBLE
        binding.fragNoConnectionProgressBar.visibility = visibility
    }

}