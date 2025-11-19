package com.example.truckercore.layers.presentation.nav_login.view_model.login.effect

import com.example.truckercore.domain._shared._contracts.Effect

sealed class LoginEffect : Effect {

    data object ClearFocusAndHideKeyboard : com.example.truckercore.presentation.viewmodels.view_models.login.effect.LoginEffect()

    data class ShowToast(val message: String) : com.example.truckercore.presentation.viewmodels.view_models.login.effect.LoginEffect()

    data object NavigateToMain : com.example.truckercore.presentation.viewmodels.view_models.login.effect.LoginEffect()

    data object NavigateToNewUser : com.example.truckercore.presentation.viewmodels.view_models.login.effect.LoginEffect()

    data object NavigateToForgetPassword : com.example.truckercore.presentation.viewmodels.view_models.login.effect.LoginEffect()

    data object NavigateToNotification: com.example.truckercore.presentation.viewmodels.view_models.login.effect.LoginEffect()

}