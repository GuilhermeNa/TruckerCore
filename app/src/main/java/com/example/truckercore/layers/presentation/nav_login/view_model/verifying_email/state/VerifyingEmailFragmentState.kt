package com.example.truckercore.layers.presentation.nav_login.view_model.verifying_email.state

import com.example.truckercore.layers.presentation.base.contracts.State

sealed class VerifyingEmailFragmentState: State {

    data object Initial: VerifyingEmailFragmentState()

    data object EmailFound: VerifyingEmailFragmentState()

    data object SendingEmail: VerifyingEmailFragmentState()

    data object EmailSent: VerifyingEmailFragmentState()

}