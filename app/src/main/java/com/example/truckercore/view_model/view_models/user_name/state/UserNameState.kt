package com.example.truckercore.view_model.view_models.user_name.state

import com.example.truckercore.view_model._shared._contracts.State

/**
 * Sealed class to represent the different states of the [UserNameFragment].
 * Collected and handled by the [UserNameViewModel] to manage fragment interactions.
 */
data class UserNameState(
    val components: UserNameComponents = UserNameComponents(),
    val status: UserNameStatus = UserNameStatus.Idle
) : State {

    fun idle() = updateStatus(UserNameStatus.Idle)

    fun creating() = updateStatus(UserNameStatus.Creating)

    private fun updateStatus(newStatus: UserNameStatus) = copy(status = newStatus)

    fun updateName(name: String): UserNameState {
        val newComponents = components.updateName(name)
        return copy(components = newComponents)
    }

}


