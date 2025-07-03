package com.example.truckercore.view_model.view_models.verifying_email.effect

sealed class VerifyingEmailUiEffect: VerifyingEmailEffect {
    data object StartTransitionToState2 : VerifyingEmailUiEffect()
    data object StartTransitionToState3 : VerifyingEmailUiEffect()
    data object NavigateToCreateNameFragment : VerifyingEmailUiEffect()
    data object NavigateToErrorActivity : VerifyingEmailUiEffect()
}
