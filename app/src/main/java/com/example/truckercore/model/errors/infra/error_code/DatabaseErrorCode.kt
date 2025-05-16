package com.example.truckercore.model.errors.infra.error_code

import com.example.truckercore.model.errors.infra.contracts.InfraErrorCode

sealed class DatabaseErrorCode: InfraErrorCode {

    data object ApiError: DatabaseErrorCode()
    data object ImplementationError: DatabaseErrorCode()
    data object Unknown: DatabaseErrorCode()

}