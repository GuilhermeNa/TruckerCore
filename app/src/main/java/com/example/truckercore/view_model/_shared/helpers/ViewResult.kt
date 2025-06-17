package com.example.truckercore.view_model._shared.helpers

sealed class ViewResult<out T> {

    data class Success<T>(val data: T) : ViewResult<T>()

    data class Error(val e: ViewError) : ViewResult<Nothing>()

    fun isSuccess() = this is Success

}