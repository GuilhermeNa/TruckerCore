package com.example.truckercore.view_model.view_models.user_name.state

import com.example.truckercore._shared.classes.FullName
import com.example.truckercore.view_model._shared._base.managers.StateManager

class UserNameStateManager : StateManager<UserNameState>(UserNameState()) {

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