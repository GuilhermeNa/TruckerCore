package com.example.truckercore.view_model._shared.helpers

sealed class ViewResult<out T> {

    data class Success<T>(val data: T) : ViewResult<T>()

    data class Error(val e: ViewError) : ViewResult<Nothing>()

    fun isSuccess() = this is Success

}

sealed class UseCaseResult<out T> {

    data class Success<T>(val data: T) : UseCaseResult<T>()

    sealed class Error : UseCaseResult<Nothing>() {

        data class Recoverable(val message: String) : Error()

        data class Critical(val exception: Throwable, val message: String? = null) : Error()
    }

    val isSuccess get() = this is Success
    val isError get() = this is Error
    val isRecoverable get() = this is Error.Recoverable
    val isCritical get() = this is Error.Critical
}


fun t() {

    UseCaseResult.Error.Critical()

}