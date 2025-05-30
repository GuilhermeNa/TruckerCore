package com.example.truckercore.view_model.view_models.login.effect

import com.example.truckercore._utils.classes.contracts.Effect

sealed class LoginEffect : Effect {

    data object ClearFocusAndHideKeyboard : LoginEffect()

    data class ShowToast(val message: String) : LoginEffect()

    data object NavigateToMain : LoginEffect()

    data object NavigateToNewUser : LoginEffect()

    data object NavigateToForgetPassword : LoginEffect()

    data object NavigateToNotification: LoginEffect()

}