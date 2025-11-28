package com.example.truckercore.layers.presentation.nav_login.view.fragments.verifying_email

import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import com.example.truckercore.core.my_lib.classes.Email
import com.example.truckercore.databinding.FragmentVerifyingEmailBinding
import com.example.truckercore.layers.presentation.base.handlers.StateHandler
import com.example.truckercore.layers.presentation.nav_login.view_model.verifying_email.state.VerifyingEmailFragmentState

class VerifyingEmailFragmentStateHandler : StateHandler<FragmentVerifyingEmailBinding>() {

    fun bindEmail(email: Email) {
        binding.fragVerifyingEmailText.text = email.value
    }

    fun handleState(state: VerifyingEmailFragmentState, onVerified: () -> Unit) {
        when (state) {
            VerifyingEmailFragmentState.Initial -> {
                showShimmer(true)
                showEmailLayout(false)
                showButtonLayout(false)
                showTimerLayout(false)
                showVerifiedLayout(false)
            }

            VerifyingEmailFragmentState.EmailFound -> {
                showShimmer(false)
                showEmailLayout(true)
                showButtonLayout(true)
                showTimerLayout(false)
                showVerifiedLayout(false)
            }

            VerifyingEmailFragmentState.SendingEmail -> {
                showShimmer(false)
                showEmailLayout(true)
                showButtonLayout(true)
                showTimerLayout(false)
                showVerifiedLayout(false)
            }

            VerifyingEmailFragmentState.VerifyingEmail -> {
                showShimmer(false)
                showEmailLayout(true)
                showButtonLayout(false)
                showTimerLayout(true)
                showVerifiedLayout(false)
            }

            VerifyingEmailFragmentState.EmailVerified -> {
                showShimmer(false)
                showEmailLayout(true)
                showButtonLayout(false)
                showTimerLayout(false)
                showVerifiedLayout(true)
                onVerified()
            }
        }
    }

    private fun showShimmer(show: Boolean) {
        // Scope val's
        val shimmerEmail = binding.fragVerifyingEmailEmailShimmer
        val shimmerButton = binding.fragVerifyingEmailButtonShimmer

        // Show Shimmers and grants visibility
        if (show) {
            shimmerEmail.visibility = VISIBLE
            shimmerEmail.showShimmer(true)

            shimmerButton.visibility = VISIBLE
            shimmerButton.showShimmer(true)

            return
        }

        // Remove Shimmers when is not needed
        shimmerEmail.visibility = GONE
        shimmerEmail.hideShimmer()
        shimmerButton.visibility = GONE
        shimmerButton.hideShimmer()

    }

    private fun showEmailLayout(show: Boolean) {
        binding.fragVerifyingEmailLayout.visibility = if (show) VISIBLE else INVISIBLE
    }

    private fun showButtonLayout(show: Boolean) {
        binding.fragVerifyingEmailButtonLayout.visibility = if (show) VISIBLE else INVISIBLE
    }

    private fun showTimerLayout(show: Boolean) {
        binding.fragVerifyingEmailTimerLayout.visibility = if (show) VISIBLE else INVISIBLE
    }

    private fun showVerifiedLayout(show: Boolean) {
        binding.fragVerifyingEmailVerifiedLayout.visibility = if (show) VISIBLE else INVISIBLE
    }

    fun animateProgress(value: Int) {
        binding.fragVerifyingEmailProgressBar.setProgressCompat((PROGRESS_MAX - value), true)
        binding.fragVerifyingEmailTimer.text = "$value"
    }

    fun jumpToProgress(value: Int) {
        binding.fragVerifyingEmailProgressBar.setProgressCompat((PROGRESS_MAX - value), false)
        binding.fragVerifyingEmailTimer.text = "$value"
    }

    companion object {
        private const val PROGRESS_MAX = 61
    }

}
