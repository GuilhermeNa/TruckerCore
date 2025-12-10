package com.example.truckercore.layers.presentation.nav_login.view_model.login.helpers

sealed class LoginFragmentStatus {

    data object WaitingInput: LoginFragmentStatus()

    data object ReadyToLogin: LoginFragmentStatus()

    data object TryingLogin: LoginFragmentStatus()

}