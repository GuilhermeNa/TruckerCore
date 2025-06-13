package com.example.truckercore.view_model.view_models.splash.state

import com.example.truckercore.view_model._shared._contracts.State
import com.example.truckercore.view_model._shared.components.TextComponent
import com.example.truckercore.view_model._shared.helpers.ViewError

data class SplashState(
    val nameComponent: TextComponent,
    val status: SplashStatus
) : State {



}