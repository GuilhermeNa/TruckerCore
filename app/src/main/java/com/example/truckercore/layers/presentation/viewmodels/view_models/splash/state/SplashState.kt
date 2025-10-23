package com.example.truckercore.layers.presentation.viewmodels.view_models.splash.state

import com.example.truckercore.domain._shared.components.TextComponent

data class SplashState(
    val nameComponent: TextComponent = TextComponent(),
    val status: com.example.truckercore.presentation.viewmodels.view_models.splash.state.SplashStatus = com.example.truckercore.presentation.viewmodels.view_models.splash.state.SplashStatus.Initial
) : com.example.truckercore.presentation.viewmodels._shared._contracts.State {

    fun updateName(appName: String): com.example.truckercore.presentation.viewmodels.view_models.splash.state.SplashState {
        val newNameComponent = nameComponent.updateText(appName)
        return copy(nameComponent = newNameComponent)
    }

    fun loading(): com.example.truckercore.presentation.viewmodels.view_models.splash.state.SplashState {
        return copy(status = com.example.truckercore.presentation.viewmodels.view_models.splash.state.SplashStatus.Loading)
    }

    fun loaded(): com.example.truckercore.presentation.viewmodels.view_models.splash.state.SplashState {
        return copy(status = com.example.truckercore.presentation.viewmodels.view_models.splash.state.SplashStatus.Loaded)
    }

    override fun toString(): String {
        return "SplashState(name=\"${nameComponent.text}\", status=$status)"
    }

}