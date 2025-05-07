package com.example.truckercore.view_model.view_models.login

import com.example.truckercore.model.errors.AppException
import com.example.truckercore.model.errors.ErrorCode
import com.example.truckercore.view_model.view_models.splash.SplashEffect.AlreadyAccessed
import com.example.truckercore.view_model.view_models.splash.SplashEffect.FirstTimeAccess

sealed class LoginEffect {

    data object ClearFocusAndHideKeyboard : LoginEffect()

    sealed class Navigation : LoginEffect() {
        data object EnterSystem : Navigation()
        data object CompleteRegister : Navigation()
        data object NewAccount : Navigation()
        data object ForgetPassword : Navigation()
    }

    data class Error(val error: ErrorCode) : LoginEffect()

    fun isFragmentNavigation() = this is Navigation.CompleteRegister ||
            this is Navigation.NewAccount ||
            this is Navigation.ForgetPassword


}