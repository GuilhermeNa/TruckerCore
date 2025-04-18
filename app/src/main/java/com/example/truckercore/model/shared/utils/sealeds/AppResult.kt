package com.example.truckercore.model.shared.utils.sealeds

import com.example.truckercore.model.infrastructure.integration.exceptions.AppException

sealed class AppResult<out T> {

    data class Success<T>(val data: T) : AppResult<T>()

    data class Error(val exception: AppException) : AppResult<Nothing>()

}