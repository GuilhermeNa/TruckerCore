package com.example.truckercore.infrastructure.security.authentication.service

import com.example.truckercore.infrastructure.database.firebase.repository.FirebaseAuthRepository
import com.example.truckercore.infrastructure.security.authentication.entity.Credentials
import com.example.truckercore.infrastructure.security.authentication.entity.NewAccessRequirements
import com.example.truckercore.infrastructure.security.authentication.use_cases.CreateNewSystemAccessUseCase
import com.example.truckercore.infrastructure.util.ExceptionHandler
import com.example.truckercore.shared.abstractions.Service
import com.example.truckercore.shared.utils.sealeds.Response
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

internal class AuthServiceImpl(
    override val exceptionHandler: ExceptionHandler,
    private val firebaseAuthRepository: FirebaseAuthRepository,
    private val createSystemAccess: CreateNewSystemAccessUseCase
) : Service(exceptionHandler), AuthService {

    override fun authenticateCredentials(credentials: Credentials): Flow<Response<String>> =
        runSafe {
            firebaseAuthRepository.authenticateWithEmail(credentials.email, credentials.password)
        }

    override fun signIn(credentials: Credentials): Flow<Response<Unit>> =
        runSafe { firebaseAuthRepository.signIn(credentials.email, credentials.password) }

    override fun signOut() {
        firebaseAuthRepository.signOut()
    }

    override fun getCurrentUser(): FirebaseUser? = firebaseAuthRepository.getCurrentUser()

    override fun createNewSystemAccess(requirements: NewAccessRequirements) =
        runSafe { createSystemAccess.execute(requirements) }

}