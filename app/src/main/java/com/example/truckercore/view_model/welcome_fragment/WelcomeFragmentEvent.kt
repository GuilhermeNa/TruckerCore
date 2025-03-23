package com.example.truckercore.view_model.welcome_fragment

sealed class WelcomeFragmentEvent {

    data object LeftFabCLicked : WelcomeFragmentEvent()
    data object RightFabClicked : WelcomeFragmentEvent()
    data object TopButtonClicked: WelcomeFragmentEvent()

}