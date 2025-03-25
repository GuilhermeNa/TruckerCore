package com.example.truckercore.view.sealeds

sealed class AppError {
    data object NetworkError : AppError()
    data object UnknownError : AppError()
    data class CustomError(val message: String) : AppError()
}