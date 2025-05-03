package com.example.truckercore.model.shared.utils.sealeds

import com.example.truckercore.model.errors.AppException

sealed class AppResponse<out T> {

    data class Success<T>(val data: T) : AppResponse<T>()

    data class Error(val exception: AppException) : AppResponse<Nothing>()

    data object Empty : AppResponse<Nothing>()

}