package com.example.truckercore.model.infrastructure.security.authentication.service

import com.example.truckercore.model.infrastructure.database.firebase.repository.FirebaseAuthRepository
import com.example.truckercore.model.infrastructure.security.authentication.entity.Credentials
import com.example.truckercore.model.infrastructure.security.authentication.entity.LoggedUserDetails
import com.example.truckercore.model.infrastructure.security.authentication.entity.NewAccessRequirements
import com.example.truckercore.model.infrastructure.security.authentication.errors.NullFirebaseUserException
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.CreateNewSystemAccessUseCase
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.GetLoggedUserDetailsUseCase
import com.example.truckercore.model.infrastructure.util.ExceptionHandler
import com.example.truckercore.model.shared.abstractions.Service
import com.example.truckercore.model.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

internal class AuthServiceImpl(
    override val exceptionHandler: ExceptionHandler,
    private val firebaseAuthRepository: FirebaseAuthRepository,
    private val createSystemAccess: CreateNewSystemAccessUseCase,
    private val getLoggedUser: GetLoggedUserDetailsUseCase
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

    override fun thereIsLoggedUser(): Boolean {
        return firebaseAuthRepository.getCurrentUser() != null
    }

    override fun createNewSystemAccess(requirements: NewAccessRequirements) =
        runSafe { createSystemAccess.execute(requirements) }

    override fun getLoggedUserDetails(): Flow<Response<LoggedUserDetails>> = runSafe {
        firebaseAuthRepository.getCurrentUser()?.let { fbUser ->
            getLoggedUser.execute(fbUser.uid)
        } ?: firebaseUserErrorFlow()
    }

    private fun firebaseUserErrorFlow() = flowOf(
        Response.Error(NullFirebaseUserException("Firebase returned a null FirebaseUser."))
    )

}