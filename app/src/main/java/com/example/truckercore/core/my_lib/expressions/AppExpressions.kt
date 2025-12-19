package com.example.truckercore.core.my_lib.expressions

import com.example.truckercore.core.error.DomainException
import com.example.truckercore.core.error.InfraException
import com.example.truckercore.core.error.core.AppException

fun AppException.isByNetwork() = this is InfraException.Network

fun AppException.isByInvalidCredentials() = this is DomainException.InvalidCredentials

fun AppException.isByWeakPassword() = this is DomainException.WeakPassword

fun AppException.isByUserCollision() = this is DomainException.UserCollision