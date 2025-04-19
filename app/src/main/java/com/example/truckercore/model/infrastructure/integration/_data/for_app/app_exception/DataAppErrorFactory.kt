package com.example.truckercore.model.infrastructure.integration._data.for_app.app_exception

import com.example.truckercore.model.infrastructure.app_exception.AppException
import com.example.truckercore.model.infrastructure.app_exception.ErrorFactory

class DataAppErrorFactory: ErrorFactory {

    operator fun invoke(e: Throwable): AppException {
        TODO("Not yet implemented")
    }

}