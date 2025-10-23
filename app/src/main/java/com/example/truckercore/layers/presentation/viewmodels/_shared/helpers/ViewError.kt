package com.example.truckercore.layers.presentation.viewmodels._shared.helpers

sealed class ViewError {

    data class Recoverable(val message: String) : ViewError()

    data object Critical : ViewError()

}