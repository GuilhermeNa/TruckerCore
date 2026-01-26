package com.example.truckercore.layers.presentation.login.view_model.splash.helpers

import com.example.truckercore.layers.presentation.base.contracts.State
import com.example.truckercore.core.my_lib.ui_components.TextComponent

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