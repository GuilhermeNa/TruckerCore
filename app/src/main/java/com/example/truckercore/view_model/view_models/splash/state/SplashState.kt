package com.example.truckercore.view_model.view_models.splash.state

import com.example.truckercore.view_model._shared._contracts.State
import com.example.truckercore.view_model._shared.components.TextComponent

data class SplashState(
    val nameComponent: TextComponent = TextComponent(),
    val status: SplashStatus = SplashStatus.Initial
) : State {

    fun updateName(appName: String): SplashState {
        val newNameComponent = nameComponent.updateText(appName)
        return copy(nameComponent = newNameComponent)
    }

    fun loading(): SplashState {
        return copy(status = SplashStatus.Loading)
    }

    fun loaded(): SplashState {
        return copy(status = SplashStatus.Loaded)
    }

    override fun toString(): String {
        return "SplashState(name=\"${nameComponent.text}\", status=$status)"
    }

}