package com.example.truckercore.layers.presentation.viewmodels.view_models.continue_register.state

import com.example.truckercore.core.classes.Email
import com.example.truckercore.domain._shared.components.TextComponent

data class ContinueRegisterState(
    val emailComponent: TextComponent = TextComponent(),
    val direction: com.example.truckercore.presentation.viewmodels.view_models.continue_register.state.ContinueRegisterDirection? = null,
    val status: ContinueRegisterStatus = ContinueRegisterStatus.Initializing
) : com.example.truckercore.presentation.viewmodels._shared._contracts.State {

    fun idle(direction: com.example.truckercore.presentation.viewmodels.view_models.continue_register.state.ContinueRegisterDirection): ContinueRegisterState {
        return copy(direction = direction, status = ContinueRegisterStatus.Idle)
    }

    fun isIdle() = status is ContinueRegisterStatus.Idle

    fun updateEmail(email: Email): ContinueRegisterState {
        val updatedComponent = TextComponent(text = email.value)
        return copy(emailComponent = updatedComponent)
    }

}