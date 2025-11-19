package com.example.truckercore.layers.presentation.nav_login.view.fragments.login.navigator

import com.example.truckercore.presentation._shared._contracts.Navigator

interface LoginNavigator : Navigator {

    fun navigateToEmailAuth()

    fun navigateToForgetPassword()

}