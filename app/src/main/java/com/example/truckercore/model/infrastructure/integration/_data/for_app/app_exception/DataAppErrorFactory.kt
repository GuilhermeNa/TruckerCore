package com.example.truckercore.model.infrastructure.integration._data.for_app.app_exception

import com.example.truckercore.model.infrastructure.app_exception.AppException
import com.example.truckercore.model.infrastructure.app_exception.ErrorFactory

object DataAppErrorFactory: ErrorFactory {

    fun handleDataSourceError(e: Throwable): AppException {
        TODO("Not yet implemented")
    }

}