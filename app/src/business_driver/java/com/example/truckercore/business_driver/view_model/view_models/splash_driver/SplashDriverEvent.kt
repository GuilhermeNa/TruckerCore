package com.example.truckercore.business_driver.view_model.view_models.splash_driver

import com.example.truckercore._utils.classes.contracts.Event

sealed class SplashDriverEvent : Event {

    data object Initialize : SplashDriverEvent()

    sealed class LoadTaskConcluded : SplashDriverEvent() {
        data object FirstAccess: LoadTaskConcluded()
        data object NotFirstAccess: LoadTaskConcluded()
        data class Error(val e: Exception) : LoadTaskConcluded()
    }

}