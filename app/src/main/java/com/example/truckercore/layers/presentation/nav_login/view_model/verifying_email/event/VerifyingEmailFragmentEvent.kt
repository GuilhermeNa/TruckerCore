package com.example.truckercore.layers.presentation.nav_login.view_model.verifying_email.event

import com.example.truckercore.layers.presentation.base.contracts.Event

sealed class VerifyingEmailFragmentEvent: Event {

    data object VerifiedUiTransitionEnd: VerifyingEmailFragmentEvent()

    data object Timeout: VerifyingEmailFragmentEvent()

    data object RetryTask: VerifyingEmailFragmentEvent()

    sealed class Click :VerifyingEmailFragmentEvent() {
        data object SendEmailButton: Click()
        data object NewEmailButton: Click()
    }

    sealed class GetEmailTask: VerifyingEmailFragmentEvent() {
        data object Complete: GetEmailTask()
        data object Failure: GetEmailTask()
        data object NotFound: GetEmailTask()
    }

    sealed class SendEmailTask: VerifyingEmailFragmentEvent() {
        data object Complete: SendEmailTask()
        data object Failure: SendEmailTask()
        data object NoConnection: SendEmailTask()
    }

    sealed class VerifyEmailTask: VerifyingEmailFragmentEvent() {
        data object Complete: VerifyEmailTask()
        data object Failure: VerifyEmailTask()
        data object NoConnection: VerifyEmailTask()
    }

}