package com.example.truckercore.view_model.view_models.continue_register.state

import com.example.truckercore._shared.classes.Email
import com.example.truckercore.view_model._shared._contracts.State
import com.example.truckercore.view_model._shared.components.TextComponent

data class ContinueRegisterState(
    val emailComponent: TextComponent = TextComponent(),
    val direction: ContinueRegisterDirection? = null,
    val status: ContinueRegisterStatus = ContinueRegisterStatus.Initializing
) : State {

    fun idle(direction: ContinueRegisterDirection): ContinueRegisterState {
        return copy(direction = direction, status = ContinueRegisterStatus.Idle)
    }

    fun isIdle() = status is ContinueRegisterStatus.Idle

    fun updateEmail(email: Email): ContinueRegisterState {
        val updatedComponent = TextComponent(text = email.value)
        return copy(emailComponent = updatedComponent)
    }

}