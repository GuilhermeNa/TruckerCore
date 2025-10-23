package com.example.truckercore.layers.presentation.viewmodels.view_models.user_name.state

import com.example.truckercore.presentation.viewmodels._shared._contracts.State

/**
 * Sealed class to represent the different states of the [UserNameFragment].
 * Collected and handled by the [UserNameViewModel] to manage fragment interactions.
 */
data class UserNameState(
    val components: com.example.truckercore.presentation.viewmodels.view_models.user_name.state.UserNameComponents = com.example.truckercore.presentation.viewmodels.view_models.user_name.state.UserNameComponents(),
    val status: com.example.truckercore.presentation.viewmodels.view_models.user_name.state.UserNameStatus = com.example.truckercore.presentation.viewmodels.view_models.user_name.state.UserNameStatus.Idle
) : com.example.truckercore.presentation.viewmodels._shared._contracts.State {

    fun idle() = updateStatus(com.example.truckercore.presentation.viewmodels.view_models.user_name.state.UserNameStatus.Idle)

    fun creating() = updateStatus(com.example.truckercore.presentation.viewmodels.view_models.user_name.state.UserNameStatus.Creating)

    private fun updateStatus(newStatus: com.example.truckercore.presentation.viewmodels.view_models.user_name.state.UserNameStatus) = copy(status = newStatus)

    fun updateName(name: String): com.example.truckercore.presentation.viewmodels.view_models.user_name.state.UserNameState {
        val newComponents = components.updateName(name)
        return copy(components = newComponents)
    }

}


