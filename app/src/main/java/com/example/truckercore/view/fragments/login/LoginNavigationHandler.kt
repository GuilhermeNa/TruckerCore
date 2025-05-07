package com.example.truckercore.view.fragments.login

import android.content.Context
import android.content.Intent
import androidx.navigation.NavDirections
import com.example.truckercore.model.configs.flavor.FlavorService
import com.example.truckercore.view.activities.NotificationActivity
import com.example.truckercore.view_model.view_models.login.LoginEffect

class LoginNavigationHandler{

    fun getDirection(effect: LoginEffect): NavDirections = when (effect) {
        LoginEffect.Navigation.CompleteRegister -> LoginFragmentDirections.actionLoginFragmentToContinueRegisterFragment()
        LoginEffect.Navigation.ForgetPassword -> LoginFragmentDirections.actionLoginFragmentToForgetPasswordFragment()
        LoginEffect.Navigation.NewAccount -> LoginFragmentDirections.actionLoginFragmentToEmailAuthFragment()
        else -> throw IllegalArgumentException("Unsupported navigation effect: ${effect::class.qualifiedName}")
    }

    fun getIntent(effect: LoginEffect, context: Context): Intent = when (effect) {
        is LoginEffect.Navigation.EnterSystem -> FlavorService.enterSystemIntent(context)

        is LoginEffect.Error -> NotificationActivity.newInstance(
            context = context,
            errorHeader = effect.error.name,
            errorBody = effect.error.userMessage
        )

        else -> throw IllegalArgumentException("Unsupported navigation effect: ${effect::class.qualifiedName}")
    }

}
