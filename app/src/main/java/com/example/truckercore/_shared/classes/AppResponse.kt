package com.example.truckercore._shared.classes

import com.example.truckercore._shared.classes.AppResponse.Empty
import com.example.truckercore._shared.classes.AppResponse.Failure
import com.example.truckercore._shared.classes.AppResponse.Success
import com.example.truckercore._shared.classes.AppResponse.SuccessUnit
import com.example.truckercore._shared.expressions.get
import com.example.truckercore.model.errors.AppException

sealed class AppResponse<out T> {

    data class Success<T>(val data: T) : AppResponse<T>()

    data object SuccessUnit : AppResponse<Unit>()

    data object Empty : AppResponse<Nothing>()

    data class Failure(val exception: AppException) : AppResponse<Nothing>()

    companion object {

        fun <T> success(data: T) = Success(data)

        fun failure(exception: AppException) = Failure(exception)

        val successUnit = SuccessUnit

        val empty = Empty

    }

}

inline fun <T> AppResponse<T>.onSuccess(action: (T) -> Unit): AppResponse<T> {
    if (this is Success) action(data)
    return this
}

inline fun <T> AppResponse<T>.onFailure(action: (AppException) -> Unit): AppResponse<T> {
    if (this is Failure) action(exception)
    return this
}

inline fun <T> AppResponse<T>.onEmpty(action: () -> Unit): AppResponse<T> {
    if (this is Empty) action()
    return this
}

inline fun <T> AppResponse<T>.onSuccessUnit(action: () -> Unit): AppResponse<T> {
    if (this is SuccessUnit) action()
    return this
}

inline fun <R, T> AppResponse<T>.map(
    onSuccess: (data: T) -> R,
    onSuccessUnit: () -> R,
    onEmpty: () -> R,
    onError: (e: AppException) -> R
): R = when (this) {
    is Success -> onSuccess(data)
    is SuccessUnit -> onSuccessUnit()
    is Empty -> onEmpty()
    is Failure -> onError(exception)
}

class AppResponseException(message: String) : Exception(message)