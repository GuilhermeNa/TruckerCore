package com.example.truckercore.view_model.states

sealed class FragState<out T> {

    data object Initial : FragState<Nothing>()

    data class Loaded<T>(val data: T): FragState<T>()

    data class Error(val error: Exception): FragState<Nothing>()

}