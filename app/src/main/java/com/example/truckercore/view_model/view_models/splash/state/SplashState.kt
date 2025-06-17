package com.example.truckercore.view_model.view_models.splash.state

import com.example.truckercore.view_model._shared._contracts.State
import com.example.truckercore.view_model._shared.components.TextComponent
import com.example.truckercore.view_model.view_models.splash.use_case.SplashDirection

data class SplashState(
    val nameComponent: TextComponent = TextComponent(),
    val status: SplashStatus = SplashStatus.Initial,
    val direction: SplashDirection? = null
) : State {

    fun updateName(appName: String): SplashState {
        val newNameComponent = nameComponent.updateText(appName)
        return copy(nameComponent = newNameComponent)
    }

    fun loading(): SplashState {
        return copy(status = SplashStatus.Loading)
    }

    fun loaded(newDirection: SplashDirection): SplashState {
        return copy(direction = newDirection, status = SplashStatus.Loaded)
    }

}