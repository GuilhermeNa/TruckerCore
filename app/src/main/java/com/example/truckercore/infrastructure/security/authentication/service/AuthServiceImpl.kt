package com.example.truckercore.infrastructure.security.authentication.service

import com.example.truckercore.infrastructure.database.firebase.repository.FirebaseAuthRepository
import com.example.truckercore.infrastructure.security.authentication.entity.Credentials
import com.example.truckercore.shared.utils.sealeds.Response
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

internal class AuthServiceImpl(
    private val firebaseAuthRepository: FirebaseAuthRepository
) : AuthService {

    override fun authenticateCredentials(credentials: Credentials): Flow<Response<String>> =
        firebaseAuthRepository.authenticateWithEmail(
            credentials.email, credentials.password
        )

    override fun signIn(credentials: Credentials): Flow<Response<Unit>> =
        firebaseAuthRepository.signIn(credentials.email, credentials.password)

    override fun signOut() = firebaseAuthRepository.signOut()

    override fun getCurrentUser(): FirebaseUser? = firebaseAuthRepository.getCurrentUser()

    override fun registerNewSystemAccess() {

    }

}