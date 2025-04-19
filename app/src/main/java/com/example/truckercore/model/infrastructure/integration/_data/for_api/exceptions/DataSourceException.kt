package com.example.truckercore.model.infrastructure.integration._data.for_api.exceptions

abstract class DataSourceException(message: String? = null, cause: Throwable? = null):
        Exception(message, cause)