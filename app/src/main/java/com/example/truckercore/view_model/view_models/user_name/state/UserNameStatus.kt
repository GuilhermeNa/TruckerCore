package com.example.truckercore.view_model.view_models.user_name.state

sealed class UserNameStatus {
    fun isCreating(): Boolean = this is Creating

    data object Idle : UserNameStatus()
    data object Creating : UserNameStatus()

}