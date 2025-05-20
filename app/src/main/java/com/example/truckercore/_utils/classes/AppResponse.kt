package com.example.truckercore._utils.classes

import com.example.truckercore.model.errors.AppException

sealed class AppResponse<out T> {

    data class Success<T>(val data: T) : AppResponse<T>()

    data object Empty : AppResponse<Nothing>()

    data class Error(val exception: AppException) : AppResponse<Nothing>()

}

class AppResponseException(message: String): Exception(message)