package com.example.truckercore.core.error.core

import com.example.truckercore.core.error.DomainException
import com.example.truckercore.core.error.InfraException

open class AppException(
    message: String? = null,
    cause: Throwable? = null
) : Exception(message, cause) {

    fun isByNetwork() = this is InfraException.Network

    fun isByInvalidCredentials() = this is DomainException.InvalidCredentials

    fun isByWeakPassword() = this is DomainException.WeakPassword

    fun isByUserCollision() = this is DomainException.UserCollision

}