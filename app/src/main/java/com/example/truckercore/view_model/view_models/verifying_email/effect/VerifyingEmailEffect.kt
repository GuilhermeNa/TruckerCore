package com.example.truckercore.view_model.view_models.verifying_email.effect

sealed class VerifyingEmailEffect {

    sealed class UiEffect : VerifyingEmailEffect() {
        data class ShowMessage(val message: String) : UiEffect()
        data object NavigateToNotification : UiEffect()
        data object NavigateToUserName : UiEffect()
    }

    sealed class SystemEffect : VerifyingEmailEffect() {
        sealed class Task : SystemEffect() {
            data object LaunchVerification : Task()
            data object LaunchCounter : Task()
        }
    }

}