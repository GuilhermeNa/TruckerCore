package com.example.truckercore.layers.presentation.viewmodels.view_models.welcome_fragment.event

import com.example.truckercore.layers.presentation.viewmodels.base._contracts.Event

sealed class WelcomeFragmentEvent : Event {

    sealed class Click : WelcomeFragmentEvent() {
        data object LeftFab : Click()
        data object RightFab : Click()
        data object JumpButton : Click()
    }

    data class PagerChanged(val position: Int) : WelcomeFragmentEvent()

}