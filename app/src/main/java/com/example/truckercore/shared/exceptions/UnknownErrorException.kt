package com.example.truckercore.shared.exceptions

class UnknownErrorException(message: String? = null, throwable: Throwable? = null) :
    Exception(message, throwable)