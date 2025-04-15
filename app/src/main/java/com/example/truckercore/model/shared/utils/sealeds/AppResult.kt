package com.example.truckercore.model.shared.utils.sealeds

import com.example.truckercore.model.shared.errors._main.AppException

sealed class AppResult<out T> {

    data class Success<T>(val data: T) : AppResult<T>()

    data class Error(val exception: AppException) : AppResult<Nothing>()

}