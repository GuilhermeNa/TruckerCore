package com.example.truckercore.model.shared.utils.sealeds

import com.example.truckercore.model.errors.AppExceptionOld

sealed class AppResult<out T> {

    data class Success<T>(val data: T) : AppResult<T>()

    data class Error(val exception: AppExceptionOld) : AppResult<Nothing>()

    val isSuccess get(): Boolean = this is Success

    val isError get(): Boolean = this is Error
}