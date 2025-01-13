package com.example.truckercore.shared.errors

abstract class RemovalException(message: String? = null, cause: Exception? = null) :
    Exception(message, cause)