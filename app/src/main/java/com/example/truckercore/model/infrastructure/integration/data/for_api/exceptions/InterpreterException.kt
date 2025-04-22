package com.example.truckercore.model.infrastructure.integration.data.for_api.exceptions

class InterpreterException(message: String? = null, cause: Throwable? = null) :
    DataSourceException(message, cause)