package com.example.truckercore.view_model.view_models.welcome_fragment

import com.example.truckercore._utils.classes.contracts.Event

sealed class WelcomeEvent: Event {

    data class PagerChanged(val position: Int): WelcomeEvent()

}