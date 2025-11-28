package com.example.truckercore.layers.presentation.nav_login.view_model.verifying_email.state

import com.example.truckercore.layers.presentation.base.contracts.State

sealed class VerifyingEmailFragmentState : State {

    data object Initial : VerifyingEmailFragmentState()

    data object EmailFound : VerifyingEmailFragmentState()

    data object SendingEmail : VerifyingEmailFragmentState()

    data object VerifyingEmail : VerifyingEmailFragmentState()

    data object EmailVerified : VerifyingEmailFragmentState()

    fun emailFound(): EmailFound {
        if (this !is Initial) throw IllegalStateException()
        return EmailFound
    }

    fun sendingEmail(): VerifyingEmailFragmentState {
        if (this !is EmailFound) throw IllegalStateException()
        return SendingEmail
    }

    fun verifyingEmail(): VerifyingEmailFragmentState {
        if (this !is SendingEmail) throw IllegalStateException()
        return VerifyingEmail
    }

    fun emailVerified(): VerifyingEmailFragmentState {
        if (this !is SendingEmail && this !is VerifyingEmail) throw IllegalStateException()
        return EmailVerified
    }

}