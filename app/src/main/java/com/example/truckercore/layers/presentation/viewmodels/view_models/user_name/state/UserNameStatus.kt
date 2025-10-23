package com.example.truckercore.layers.presentation.viewmodels.view_models.user_name.state

sealed class UserNameStatus {
    fun isCreating(): Boolean = this is com.example.truckercore.presentation.viewmodels.view_models.user_name.state.UserNameStatus.Creating

    data object Idle : com.example.truckercore.presentation.viewmodels.view_models.user_name.state.UserNameStatus()
    data object Creating : com.example.truckercore.presentation.viewmodels.view_models.user_name.state.UserNameStatus()

}