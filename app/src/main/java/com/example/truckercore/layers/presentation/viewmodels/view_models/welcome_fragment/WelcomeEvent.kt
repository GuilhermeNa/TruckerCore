package com.example.truckercore.layers.presentation.viewmodels.view_models.welcome_fragment

import com.example.truckercore.presentation.viewmodels._shared._contracts.Event

sealed class WelcomeEvent:
    com.example.truckercore.presentation.viewmodels._shared._contracts.Event {

    data class PagerChanged(val position: Int): WelcomeEvent()

}