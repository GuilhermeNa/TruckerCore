package com.example.truckercore.view_model.view_models.user_name.state

import com.example.truckercore.view_model._shared._contracts.State

/**
 * Sealed class to represent the different states of the [UserNameFragment].
 * Collected and handled by the [UserNameViewModel] to manage fragment interactions.
 */
data class UserNameState(
    val components: UserNameComponents = UserNameComponents(),
    val status: Status = Status.Idle
) : State {

    fun idle() = updateStatus(Status.Idle)

    fun creating() = updateStatus(Status.Creating)

    private fun updateStatus(newStatus: Status) = copy(status = newStatus)

    fun updateName(name: String): UserNameState {
        val newComponents = components.updateName(name)
        return copy(components = newComponents)
    }

    sealed class Status {
        data object Idle : Status()
        data object Creating : Status()
    }

}


