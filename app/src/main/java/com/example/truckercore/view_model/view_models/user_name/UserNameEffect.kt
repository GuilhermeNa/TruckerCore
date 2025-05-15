package com.example.truckercore.view_model.view_models.user_name

sealed class UserNameEffect {

    data class RecoverableError(val message: String) : UserNameEffect()

}