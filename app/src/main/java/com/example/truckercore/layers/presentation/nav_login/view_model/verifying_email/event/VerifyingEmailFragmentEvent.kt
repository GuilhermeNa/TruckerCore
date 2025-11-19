package com.example.truckercore.layers.presentation.nav_login.view_model.verifying_email.event

import com.example.truckercore.layers.presentation.base.contracts.Event

sealed class VerifyingEmailFragmentEvent: Event {

    sealed class GetEmailTask: VerifyingEmailFragmentEvent() {
        data object Complete: GetEmailTask()
        data object Failure: GetEmailTask()
        data object Empty: GetEmailTask()
    }

    sealed class SendVerificationTask: VerifyingEmailFragmentEvent() {
        data object Complete: SendVerificationTask()
        data object Failure: SendVerificationTask()
        data object NoConnection: SendVerificationTask()
    }

    sealed class CheckEmailTask: VerifyingEmailFragmentEvent() {
        data object Complete: CheckEmailTask()
        data object Failure: CheckEmailTask()
        data object NoConnection: CheckEmailTask()
    }

}