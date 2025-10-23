package com.example.truckercore.layers.presentation.viewmodels.view_models.user_name.state

import com.example.truckercore.core.classes.FullName
import com.example.truckercore.domain._shared._base.managers.StateManager

class UserNameStateManager : StateManager<com.example.truckercore.presentation.viewmodels.view_models.user_name.state.UserNameState>(
    com.example.truckercore.presentation.viewmodels.view_models.user_name.state.UserNameState()
) {

    fun updateComponentsOnNameChange(name: String) {
        val newState = getStateValue().updateName(name)
        setState(newState)
    }

    fun setCreatingState() {
        val newState = getStateValue().creating()
        setState(newState)
    }

    fun setIdleState() {
        val newState = getStateValue().idle()
        setState(newState)
    }

    fun getName() = FullName.from(getStateValue().components.nameComponent.text)

}