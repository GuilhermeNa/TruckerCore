package com.example.truckercore.view_model.view_models.verifying_email.effect

import com.example.truckercore.view_model._shared._contracts.Effect

interface VerifyingEmailEffect: Effect

//--------------------------------------------------------------------------------------------------
// System Effects
//--------------------------------------------------------------------------------------------------
sealed class VerifyingEmailSystemEffect: VerifyingEmailEffect {
    data object LaunchSendEmailTask : VerifyingEmailSystemEffect()
    data object LaunchCheckEmailTask : VerifyingEmailSystemEffect()
}

//--------------------------------------------------------------------------------------------------
// Ui Effects
//--------------------------------------------------------------------------------------------------
sealed class VerifyingEmailUiEffect: VerifyingEmailEffect {
    data object NavigateToCreateNewEmailFragment : VerifyingEmailUiEffect()
    data object NavigateToCreateNameFragment : VerifyingEmailUiEffect()
    data object NavigateToNoConnectionFragment: VerifyingEmailUiEffect()
    data object NavigateToErrorActivity : VerifyingEmailUiEffect()
    data object ShowBottomSheet: VerifyingEmailUiEffect()
}