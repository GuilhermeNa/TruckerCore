package com.example.truckercore.view_model.view_models.verifying_email.effect

sealed class VerifyingEmailSystemEffect: VerifyingEmailEffect {
    data object LaunchSendEmailTask : VerifyingEmailSystemEffect()
    data object LaunchCheckEmailTask : VerifyingEmailSystemEffect()
}