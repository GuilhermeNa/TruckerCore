package com.example.truckercore.model.infrastructure.integration.data.for_api.exceptions

class NetworkException(message: String? = null, cause: Throwable? = null) :
    DataSourceException(message, cause)