package com.example.truckercore.view.nav_login.fragments.login.navigator

import android.app.Activity
import com.example.truckercore.view_model._shared.components.ButtonComponent
import java.lang.ref.WeakReference

interface LoginFragmentStrategy {

    fun navigateToEmailAuth()

    fun navigateToForgetPassword()

    fun navigateToMain(nActivity: WeakReference<Activity>)

    fun getNewAccountButtonComponent(): ButtonComponent

}