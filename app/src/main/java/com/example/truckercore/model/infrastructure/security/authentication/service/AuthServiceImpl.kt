package com.example.truckercore.model.infrastructure.security.authentication.service

import com.example.truckercore.model.infrastructure.database.firebase.repository.FirebaseAuthRepository
import com.example.truckercore.model.infrastructure.security.authentication.entity.Credentials
import com.example.truckercore.model.infrastructure.security.authentication.entity.SessionInfo
import com.example.truckercore.model.infrastructure.security.authentication.entity.NewAccessRequirements
import com.example.truckercore.model.infrastructure.security.authentication.errors.NullFirebaseUserException
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.CreateNewSystemAccessUseCase
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.GetSessionInfoUseCase
import com.example.truckercore.model.infrastructure.util.ExceptionHandler
import com.example.truckercore.model.shared.abstractions.Service
import com.example.truckercore.model.shared.utils.sealeds.Response
import com.google.firebase.auth.PhoneAuthCredential
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf

internal class AuthServiceImpl(
    override val exceptionHandler: ExceptionHandler,
    private val firebaseAuthRepository: FirebaseAuthRepository,
    private val createSystemAccess: CreateNewSystemAccessUseCase,
    private val getLoggedUser: GetSessionInfoUseCase
) : Service(exceptionHandler), AuthService {

    override fun createUserWithEmailAndPassword(credentials: Credentials): Flow<Response<String>> =
        runSafe {
            firebaseAuthRepository.createUserWithEmail(
                email = credentials.email,
                password = credentials.password
            )
        }

    override fun createUserWithPhone(phoneAuthCredential: PhoneAuthCredential): Flow<Response<String>> =
        runSafe {
            firebaseAuthRepository.createUserWithPhone(phoneAuthCredential)
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun signIn(credentials: Credentials): Flow<Response<SessionInfo>> = runSafe {
        firebaseAuthRepository.signIn(credentials.email, credentials.password)
            .flatMapConcat { response ->
                if (response !is Response.Success) return@flatMapConcat flowOf(Response.Empty)
                getLoggedUserFromFirebase()
            }
    }

    override fun signOut() { firebaseAuthRepository.signOut() }

    override fun thereIsLoggedUser(): Boolean {
        return firebaseAuthRepository.getCurrentUser() != null
    }

    override fun createNewSystemAccess(requirements: NewAccessRequirements) =
        runSafe { createSystemAccess.execute(requirements) }

    override fun getSessionInfo(): Flow<Response<SessionInfo>> = runSafe {
        getLoggedUserFromFirebase()
    }

    private fun getLoggedUserFromFirebase(): Flow<Response<SessionInfo>> =
        firebaseAuthRepository.getCurrentUser()?.let { fbUser ->
            getLoggedUser.execute(fbUser.uid)
        } ?: flowOf(
            Response.Error(
                NullFirebaseUserException(
                    "Firebase returned a null FirebaseUser."
                )
            )
        )

}