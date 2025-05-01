package com.example.truckercore.view.fragments.verifying_email

import com.example.truckercore.databinding.FragmentVerifyingEmailBinding
import com.example.truckercore.view_model.view_models.verifying_email.VerifyingEmailUiState

class VerifyingEmailUiStateHandler(
    private val email: String,
    private val binding: FragmentVerifyingEmailBinding
) {

    fun render(state: VerifyingEmailUiState) {
        when (state) {
            is VerifyingEmailUiState.Verified -> runTransition()
            else -> bindEmailText()
        }
    }

    private fun runTransition() {
        binding.fragVerifyingEmailMotionLayout.transitionToEnd()
    }

    private fun bindEmailText() {
        binding.fragVerifyingEmailTextEmail.text = email
    }

    fun incrementProgress(value: Int, animated: Boolean) {
        binding.fragVerifyingEmailProgressbar.setProgressCompat((PROGRESS_MAX - value), animated)
        binding.fragVerifyingEmailTimer.text = "$value"
    }

    companion object {
        private const val PROGRESS_MAX = 61
    }

}