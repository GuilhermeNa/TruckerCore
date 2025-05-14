package com.example.truckercore.model.errors.exceptions.technical

import com.example.truckercore.model.errors.exceptions.AppException

sealed class TechnicalException(message: String? = null, cause: Throwable? = null) :
    AppException(message, cause) {

    class MappingDtoToEntity(message: String? = null, cause: Throwable? = null) :
        TechnicalException(message, cause)

    class MappingEntityToDto(message: String? = null, cause: Throwable? = null) :
        TechnicalException(message, cause)

    class Factory(message: String? = null, cause: Throwable? = null) :
        TechnicalException( message,cause)

    class NotImplemented(message: String? = null, cause: Throwable? = null) :
        TechnicalException(message, cause)

    class Unknown(message: String? = null, cause: Throwable? = null) :
        TechnicalException(message, cause)

}