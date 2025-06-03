package com.example.truckercore.view.nav_login.fragments.login.navigator

import android.app.Activity
import com.example.truckercore.view._shared._contracts.Navigator
import java.lang.ref.WeakReference

interface LoginNavigator : Navigator {

    fun navigateToEmailAuth()

    fun navigateToForgetPassword()

    fun navigateToMain(activity: WeakReference<Activity>)

}