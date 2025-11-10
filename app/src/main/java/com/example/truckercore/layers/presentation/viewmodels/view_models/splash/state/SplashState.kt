package com.example.truckercore.layers.presentation.viewmodels.view_models.splash.state

import com.example.truckercore.layers.presentation.viewmodels.base._contracts.State
import com.example.truckercore.layers.presentation.viewmodels.base.components.TextComponent

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

}