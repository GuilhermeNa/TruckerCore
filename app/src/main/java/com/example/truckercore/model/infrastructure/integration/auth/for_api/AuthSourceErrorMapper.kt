package com.example.truckercore.model.infrastructure.integration.auth.for_api

import com.example.truckercore.model.infrastructure.integration.auth.for_api.exceptions.AuthSourceException

interface AuthSourceErrorMapper {

    fun creatingUserWithEmail(e: Throwable): AuthSourceException

    fun sendingEmailVerification(e: Throwable): AuthSourceException

    fun updatingProfile(e: Throwable): AuthSourceException

    fun signingInWithEmail(e: Throwable): AuthSourceException

}