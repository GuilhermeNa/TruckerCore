package com.example.truckercore.view_model.view_models.forget_password

sealed class ForgetPasswordEffect {

    data class Error(val message: String) : ForgetPasswordEffect()

}