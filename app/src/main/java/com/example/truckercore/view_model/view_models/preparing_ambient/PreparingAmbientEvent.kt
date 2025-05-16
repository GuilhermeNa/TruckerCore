package com.example.truckercore.view_model.view_models.preparing_ambient

sealed class PreparingAmbientEvent {

    data object LoadSessionRequest : PreparingAmbientEvent()

    data object SessionLoaded: PreparingAmbientEvent()

    data class ReceivedApiResult(val result: Result<Boolean>): PreparingAmbientEvent()

}