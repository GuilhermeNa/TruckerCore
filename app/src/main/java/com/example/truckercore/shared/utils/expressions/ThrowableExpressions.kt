package com.example.truckercore.shared.utils.expressions

import com.example.truckercore.shared.utils.sealeds.Response

fun Throwable.handleUnexpectedError(): Response.Error {
    this as Exception
    logError(
        context = javaClass,
        exception = this,
        message = "An unexpected error occurred during execution."
    )
    return Response.Error(this)
}