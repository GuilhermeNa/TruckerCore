package com.example.truckercore.layers.presentation.viewmodels.view_models.verifying_email.effect

import com.example.truckercore.domain._shared._contracts.Effect

interface VerifyingEmailEffect: Effect

//--------------------------------------------------------------------------------------------------
// System Effects
//--------------------------------------------------------------------------------------------------
sealed class VerifyingEmailSystemEffect:
    com.example.truckercore.presentation.viewmodels.view_models.verifying_email.effect.VerifyingEmailEffect {
    data object LaunchSendEmailTask : com.example.truckercore.presentation.viewmodels.view_models.verifying_email.effect.VerifyingEmailSystemEffect()
    data object LaunchCheckEmailTask : com.example.truckercore.presentation.viewmodels.view_models.verifying_email.effect.VerifyingEmailSystemEffect()
}

//--------------------------------------------------------------------------------------------------
// Ui Effects
//--------------------------------------------------------------------------------------------------
sealed class VerifyingEmailUiEffect:
    com.example.truckercore.presentation.viewmodels.view_models.verifying_email.effect.VerifyingEmailEffect {
    data object NavigateToCreateNewEmailFragment : com.example.truckercore.presentation.viewmodels.view_models.verifying_email.effect.VerifyingEmailUiEffect()
    data object NavigateToCreateNameFragment : com.example.truckercore.presentation.viewmodels.view_models.verifying_email.effect.VerifyingEmailUiEffect()
    data object NavigateToNoConnectionFragment: com.example.truckercore.presentation.viewmodels.view_models.verifying_email.effect.VerifyingEmailUiEffect()
    data object NavigateToErrorActivity : com.example.truckercore.presentation.viewmodels.view_models.verifying_email.effect.VerifyingEmailUiEffect()
    data object ShowBottomSheet: com.example.truckercore.presentation.viewmodels.view_models.verifying_email.effect.VerifyingEmailUiEffect()
}