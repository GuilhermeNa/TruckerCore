package com.example.truckercore.model.modules.company.app_errors

import com.example.truckercore.model.errors.AppExceptionOld

class CompanyAppException(
    message: String?,
    cause: Throwable? = null,
    errorCode: CompanyErrorCode
) : AppExceptionOld(message, cause, errorCode)