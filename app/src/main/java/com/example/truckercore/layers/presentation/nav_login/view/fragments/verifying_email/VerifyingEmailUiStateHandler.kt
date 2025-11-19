package com.example.truckercore.layers.presentation.nav_login.view.fragments.verifying_email

import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import com.example.truckercore.databinding.FragmentVerifyingEmailBinding
import com.example.truckercore.domain._shared.components.TextComponent
import com.example.truckercore.domain.view_models.verifying_email.state.VerifyingEmailState
import com.example.truckercore.domain.view_models.verifying_email.state.VerifyingEmailStatus
import com.example.truckercore.layers.presentation.base.handlers.StateHandler

class VerifyingEmailUiStateHandler : StateHandler<FragmentVerifyingEmailBinding>() {

    fun handleState(state: VerifyingEmailState) {
        bindEmail(state.email)
        bindStatus(state.status)
    }

    private fun bindEmail(email: TextComponent) {
        getBinding().fragVerifyingEmailTextEmail.text = email.text
    }

    private fun bindStatus(status: VerifyingEmailStatus) {
        when (status) {
            VerifyingEmailStatus.Idle -> {
                showShimmer(true)
                showWaitingVerificationLayout(false)
                showVerifiedLayout(false)
            }

            VerifyingEmailStatus.WaitingForVerification -> {
                showShimmer(false)
                showWaitingVerificationLayout(true)
                showVerifiedLayout(false)
            }

            VerifyingEmailStatus.EmailVerified -> {
                showShimmer(false)
                showWaitingVerificationLayout(false)
                showVerifiedLayout(true)
            }
        }
    }

    private fun showShimmer(show: Boolean) {
        val shimmer = getBinding().fragVerifyingEmailShimmer

        if (show) {
            shimmer.visibility = VISIBLE
            shimmer.showShimmer(true)
        }
        else {
            shimmer.visibility = GONE
            shimmer.hideShimmer()
        }
    }

    private fun showVerifiedLayout(show: Boolean) {
        val visibility = if (show) VISIBLE else INVISIBLE
        getBinding().fragVerifyingEmailCardCheck.visibility = visibility
        getBinding().fragVerifyingEmailTextHeader.visibility = visibility
    }

    private fun showWaitingVerificationLayout(show: Boolean) {
        val visibility = if (show) VISIBLE else INVISIBLE
        getBinding().fragVerifyingEmailLayoutTimer.visibility = visibility
        getBinding().fragVerifyingEmailHelperTextLayout.visibility = visibility
    }

    fun animateProgress(value: Int) {
        getBinding().fragVerifyingEmailProgressbar.setProgressCompat((PROGRESS_MAX - value), true)
        getBinding().fragVerifyingEmailTimer.text = "$value"
    }

    fun jumpToProgress(value: Int) {
        getBinding().fragVerifyingEmailProgressbar.setProgressCompat((PROGRESS_MAX - value), false)
        getBinding().fragVerifyingEmailTimer.text = "$value"
    }

    companion object {
        private const val PROGRESS_MAX = 61
    }

}