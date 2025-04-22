package com.example.truckercore.model.infrastructure.integration._writer.for_app.app_exception

import com.example.truckercore.model.infrastructure.app_exception.AppException
import com.example.truckercore.model.infrastructure.integration._data.for_app.app_exception.DataErrorCode

class WriterAppException(
    message: String? = null,
    cause: Throwable? = null,
    errorCode: DataErrorCode
): AppException(message, cause, errorCode)