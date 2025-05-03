package com.example.truckercore.model.modules.company.app_errors

import com.example.truckercore.model.errors.AppException

class CompanyAppException(
    message: String?,
    cause: Throwable? = null,
    errorCode: CompanyErrorCode
) : AppException(message, cause, errorCode)