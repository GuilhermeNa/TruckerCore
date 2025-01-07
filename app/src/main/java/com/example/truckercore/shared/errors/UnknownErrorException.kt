package com.example.truckercore.shared.errors

class UnknownErrorException(message: String? = null, throwable: Throwable? = null) :
    Exception(message, throwable)