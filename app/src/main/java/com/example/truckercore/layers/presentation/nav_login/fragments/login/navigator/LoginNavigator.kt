package com.example.truckercore.layers.presentation.nav_login.fragments.login.navigator

import com.example.truckercore.presentation._shared._contracts.Navigator

interface LoginNavigator : Navigator {

    fun navigateToEmailAuth()

    fun navigateToForgetPassword()

}