package com.example.truckercore.layers.presentation.login.view.fragments.verifying_email

import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import com.example.truckercore.core.my_lib.classes.Email
import com.example.truckercore.databinding.FragmentVerifyingEmailBinding
import com.example.truckercore.layers.presentation.base.handlers.StateHandler
import com.example.truckercore.layers.presentation.common.LoadingDialog
import com.example.truckercore.layers.presentation.login.view_model.verifying_email.helpers.VerifyingEmailFragmentState

/**
 * Responsible for managing all UI updates for VerifyingEmailFragment.
 *
 * This class centralizes visual state changes such as controlling layouts visibility,
 * updating shimmer loading animations, progress bar handling, and enabling/disabling buttons.
 *
 * It helps maintain a clean Fragment by separating UI logic from state observation.
 */
class VerifyingEmailFragmentStateHandler : StateHandler<FragmentVerifyingEmailBinding>() {

    /**
     * Displays the user's email in the UI.
     */
    fun bindEmail(email: Email) {
        binding.fragVerifyingEmailText.text = email.value
    }

    /**
     * Handles all UI transitions based on the current fragment state.
     *
     * @param state The current state of email verification.
     * @param dialog Loading dialog used for blocking operations.
     * @param onVerified Callback triggered when the email verified state is fully applied.
     */
    fun handleState(
        state: VerifyingEmailFragmentState,
        dialog: LoadingDialog?,
        onVerified: () -> Unit
    ) {
        when (state) {

            VerifyingEmailFragmentState.Initial -> {
                showShimmer(true)
                showEmailLayout(false)
                showButtonLayout(false)
                showTimerLayout(false)
                showVerifiedLayout(false)
            }

            VerifyingEmailFragmentState.EmailFound -> {
                dialog?.hide()
                showShimmer(false)
                showEmailLayout(true)
                showButtonLayout(true)
                enableButtons(true)
                showTimerLayout(false)
                showVerifiedLayout(false)
            }

            VerifyingEmailFragmentState.SendingEmail -> {
                dialog?.show()
                showShimmer(false)
                showEmailLayout(true)
                showButtonLayout(true)
                enableButtons(false)
                showTimerLayout(false)
                showVerifiedLayout(false)
            }

            VerifyingEmailFragmentState.VerifyingEmail -> {
                dialog?.hide()
                showShimmer(false)
                showEmailLayout(true)
                showButtonLayout(false)
                enableButtons(false)
                showTimerLayout(true)
                showVerifiedLayout(false)
            }

            VerifyingEmailFragmentState.EmailVerified -> {
                showShimmer(false)
                showEmailLayout(true)
                showButtonLayout(false)
                enableButtons(false)
                showTimerLayout(false)
                showVerifiedLayout(true)
                onVerified()
            }
        }
    }


    // ---------------------------------------------------------------------------------------------
    // UI Helpers
    // ---------------------------------------------------------------------------------------------
    /**
     * Shows or hides shimmer animations for the email and button sections.
     */
    private fun showShimmer(show: Boolean) {
        val shimmerEmail = binding.fragVerifyingEmailShimmerLayout
        val shimmerButton = binding.fragVerifyingEmailButtonShimmerLayout

        if (show) {
            shimmerEmail.visibility = VISIBLE
            shimmerEmail.showShimmer(true)
            shimmerButton.visibility = VISIBLE
            shimmerButton.showShimmer(true)
            return
        }

        shimmerEmail.visibility = GONE
        shimmerEmail.hideShimmer()
        shimmerButton.visibility = GONE
        shimmerButton.hideShimmer()
    }

    /** Controls visibility of the email container. */
    private fun showEmailLayout(show: Boolean) {
        binding.fragVerifyingEmailLayout.visibility = if (show) VISIBLE else INVISIBLE
    }

    /** Controls visibility of the main action buttons. */
    private fun showButtonLayout(show: Boolean) {
        binding.fragVerifyingEmailButtonLayout.visibility = if (show) VISIBLE else INVISIBLE
    }

    /** Enables or disables interaction with the UI buttons. */
    private fun enableButtons(enable: Boolean) {
        binding.fragVerifyingEmailButtonSend.isEnabled = enable
        binding.fragVerifyingEmailButtonNewEmail.isEnabled = enable
    }

    /** Controls visibility of the countdown timer layout. */
    private fun showTimerLayout(show: Boolean) {
        binding.fragVerifyingEmailTimerLayout.visibility = if (show) VISIBLE else INVISIBLE
    }

    /** Controls visibility of the verified confirmation layout. */
    private fun showVerifiedLayout(show: Boolean) {
        binding.fragVerifyingEmailVerifiedLayout.visibility = if (show) VISIBLE else INVISIBLE
    }

    /**
     * Updates the progress bar and timer text.
     *
     * @param value Current remaining seconds.
     * @param animated Whether the progress bar should animate the change.
     */
    fun updateProgress(value: Int, animated: Boolean) {
        binding.fragVerifyingEmailProgressBar.setProgressCompat((PROGRESS_MAX - value), animated)
        binding.fragVerifyingEmailTimer.text = "$value"
    }

    companion object {
        /** Maximum progress value for the countdown timer (1 minute). */
        private const val PROGRESS_MAX = 61
    }

}
