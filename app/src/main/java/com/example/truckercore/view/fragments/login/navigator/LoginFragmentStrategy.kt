package com.example.truckercore.view.fragments.login.navigator

import android.content.Context
import android.content.Intent
import com.example.truckercore._utils.classes.ui_component.ButtonComponent

interface LoginFragmentStrategy {

    fun navigateToEmailAuth()

    fun navigateToForgetPassword()

    fun getMainActivityIntent(context: Context): Intent

    fun getNewAccountButtonComponent(): ButtonComponent

}