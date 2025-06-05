package com.example.truckercore.view_model._shared.helpers

sealed class ViewError {

    data class Recoverable(val message: String) : ViewError()

    data object Critical : ViewError()

}