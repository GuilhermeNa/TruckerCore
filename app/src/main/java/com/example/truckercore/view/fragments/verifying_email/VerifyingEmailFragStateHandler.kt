package com.example.truckercore.view.fragments.verifying_email

import android.animation.ValueAnimator
import android.view.animation.LinearInterpolator
import com.example.truckercore.model.infrastructure.app_exception.AppException
import com.example.truckercore.model.shared.utils.expressions.handleOnUi
import com.example.truckercore.model.shared.utils.expressions.logError
import com.example.truckercore.view.activities.NotificationActivity
import com.example.truckercore.view_model.view_models.verifying_email.VerifyingEmailFragState

private typealias TryingToVerifyState = VerifyingEmailFragState.TryingToVerify
private typealias EmailVerifiedState = VerifyingEmailFragState.EmailVerified
private typealias TimeOutState = VerifyingEmailFragState.TimeOut
private typealias ErrorState = VerifyingEmailFragState.Error

class VerifyingEmailFragStateHandler(
    private val email: String,
    private val fragment: VerifyingEmailFragment
) {

    private val binding = fragment.binding
    private val currentState = fragment.lifecycle.currentState

    fun onStateChanged(state: VerifyingEmailFragState) {
        when (state) {
            is TryingToVerifyState -> handleTryingToVerifyState()
            is EmailVerifiedState -> handleEmailVerifiedState()
            is TimeOutState -> handleTimeOutState()
            is VerifyingEmailFragState.Error -> handleErrorState(state.error)
        }
    }

    private fun handleTryingToVerifyState() {
        bindHeaderText("Validando email...")
        bindEmailText()
    }

    private fun handleEmailVerifiedState() {
        bindHeaderText("Email validado!!")
    }

    private fun handleTimeOutState() {
        bindHeaderText("Validando email...")
        bindEmailText()
    }

    private fun handleErrorState(error: AppException) {
        error.errorCode.handleOnUi(
            onRecoverable = { message -> logError(message) },
            onFatalError = {
                val intent = NotificationActivity.newInstance(
                    context = fragment.requireContext(),
                    errorHeader = error.errorCode.name,
                    errorBody = error.errorCode.userMessage
                )
                fragment.startActivity(intent)
                fragment.requireActivity().finish()
            }
        )
    }

    fun incrementProgress(vle: Int) {
        binding.fragVerifyingEmailProgressbar.incrementProgressBy(1)
        binding.fragVerifyingEmailTimer.text = "$vle"
    }

    private fun bindHeaderText(text: String) {
        binding.fragVerifyingEmailTextHeader.text = text
    }

    private fun bindEmailText() {
        binding.fragVerifyingEmailTextEmail.text = email
    }

}