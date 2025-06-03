package com.example.truckercore._shared.classes

import com.example.truckercore.model.errors.AppException

sealed class AppResult<out T> {

    data class Success<T>(val data: T) : AppResult<T>()

    data class Error(val exception: AppException) : AppResult<Nothing>()

    val isSuccess get(): Boolean = this is Success

    val isError get(): Boolean = this is Error
}

class AppResultException(message: String) : Exception(message)