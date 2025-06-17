package com.example.truckercore.view_model.view_models.splash.state

import com.example.truckercore.model.shared.errors.InvalidStateException
import com.example.truckercore.view_model._shared._base.managers.StateManager
import com.example.truckercore.view_model.view_models.splash.use_case.SplashDirection

class SplashStateManager : StateManager<SplashState>(SplashState()) {

    fun updateAppNameComponent(appName: String) {
        val newState = getStateValue().updateName(appName)
        setState(newState)
    }

    fun setLoadingState() {
        val newState = getStateValue().loading()
        setState(newState)
    }

    fun setLoadedState(direction: SplashDirection) {
        val newState = getStateValue().loaded(direction)
        setState(newState)
    }

    fun getDirection(): SplashDirection {
        return getStateValue().direction
            ?: throw InvalidStateException("Direction was not set yet.")
    }

}
