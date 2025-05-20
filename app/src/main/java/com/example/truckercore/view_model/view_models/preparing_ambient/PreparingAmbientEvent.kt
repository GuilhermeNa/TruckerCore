package com.example.truckercore.view_model.view_models.preparing_ambient

import com.example.truckercore.model.errors.AppException

sealed class PreparingAmbientEvent {

    data object LoadSession : PreparingAmbientEvent()

    data object SessionLoaded : PreparingAmbientEvent()

    data class ReceivedUidError(val exception: AppException) : PreparingAmbientEvent()

    data class ReceivedSessionError(val exception: AppException) : PreparingAmbientEvent()

}