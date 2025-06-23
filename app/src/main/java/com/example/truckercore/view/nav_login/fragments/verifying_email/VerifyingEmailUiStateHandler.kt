package com.example.truckercore.view.nav_login.fragments.verifying_email

import com.example.truckercore.databinding.FragmentVerifyingEmailBinding
import com.example.truckercore.view_model.view_models.verifying_email.state.VerifyingEmailState

class VerifyingEmailUiStateHandler(
    private val email: String,
    private val binding: FragmentVerifyingEmailBinding
) {

    fun render(state: VerifyingEmailState) {
        when (state) {
            is VerifyingEmailState.Verified -> runTransition()
            else -> bindEmailText()
        }
    }

    private fun runTransition() {
        binding.fragVerifyingEmailMotionLayout.transitionToEnd()
    }

    private fun bindEmailText() {
        binding.fragVerifyingEmailTextEmail.text = email
    }

    fun animateProgress(value: Int) {
        binding.fragVerifyingEmailProgressbar.setProgressCompat((PROGRESS_MAX - value), true)
        binding.fragVerifyingEmailTimer.text = "$value"
    }

    fun jumpToProgress(value: Int) {
        binding.fragVerifyingEmailProgressbar.setProgressCompat((PROGRESS_MAX - value), false)
        binding.fragVerifyingEmailTimer.text = "$value"
    }

    companion object {
        private const val PROGRESS_MAX = 61
    }

}