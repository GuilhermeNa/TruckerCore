package com.example.truckercore.model.shared.utils.sealeds

sealed class AppResult<out T> {

    data class Success<T>(val data: T) : AppResult<T>()

    data class Error(val exception: Exception) : AppResult<Nothing>()

}