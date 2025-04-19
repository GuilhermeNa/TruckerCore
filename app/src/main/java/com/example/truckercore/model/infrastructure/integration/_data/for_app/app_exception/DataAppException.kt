package com.example.truckercore.model.infrastructure.integration._data.for_app.app_exception

import com.example.truckercore.model.infrastructure.app_exception.AppException
import com.example.truckercore.model.infrastructure.app_exception.ErrorCode

class DataAppException(
    message: String? = null,
    cause: Throwable? = null,
    errorCode: ErrorCode
): AppException(message, cause, errorCode)

