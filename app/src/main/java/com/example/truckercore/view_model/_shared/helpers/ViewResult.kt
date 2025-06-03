package com.example.truckercore.view_model._shared.helpers

sealed class ViewResult {

    data object Success : ViewResult()

    data class Error(val e: ViewError) : ViewResult()

}