package com.example.truckercore.infrastructure.security.authentication.service

import com.example.truckercore.infrastructure.security.authentication.entity.Credentials
import com.example.truckercore.infrastructure.security.authentication.entity.NewAccessRequirements
import com.example.truckercore.shared.utils.sealeds.Response
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthService {

    fun authenticateCredentials(credentials: Credentials): Flow<Response<String>>

    fun signIn(credentials: Credentials): Flow<Response<Unit>>

    fun signOut()

    fun getCurrentUser(): FirebaseUser?

    fun createNewSystemAccess(requirements: NewAccessRequirements): Flow<Response<Unit>>

}