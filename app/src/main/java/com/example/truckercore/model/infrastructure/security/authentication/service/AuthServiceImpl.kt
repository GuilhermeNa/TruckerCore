package com.example.truckercore.model.infrastructure.security.authentication.service

import com.example.truckercore.model.infrastructure.database.firebase.repository.FirebaseAuthRepository
import com.example.truckercore.model.infrastructure.security.authentication.entity.EmailAuthCredential
import com.example.truckercore.model.infrastructure.security.authentication.entity.NewAccessRequirements
import com.example.truckercore.model.infrastructure.security.authentication.entity.SessionInfo
import com.example.truckercore.model.infrastructure.security.authentication.errors.NullFirebaseUserException
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.CreateUserAndVerifyEmailUseCase
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.CreateNewSystemAccessUseCase
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.GetSessionInfoUseCase
import com.example.truckercore.model.infrastructure.util.ExceptionHandler
import com.example.truckercore.model.shared.abstractions.Service
import com.example.truckercore.model.shared.utils.sealeds.Response
import com.google.firebase.auth.PhoneAuthCredential
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext

internal class AuthServiceImpl(
    override val exceptionHandler: ExceptionHandler,
    private val authRepository: FirebaseAuthRepository,
    private val createSystemAccess: CreateNewSystemAccessUseCase,
    private val getLoggedUser: GetSessionInfoUseCase,
    private val createAndVerifyEmail: CreateUserAndVerifyEmailUseCase
) : Service(exceptionHandler), AuthService {

    override suspend fun createUserAndVerifyEmail(credential: EmailAuthCredential) =
        withContext(Dispatchers.IO) { createAndVerifyEmail.invoke(credential) }

    override suspend fun createUserWithPhone(phoneAuthCredential: PhoneAuthCredential) =
        withContext(Dispatchers.IO) { authRepository.createUserWithPhone(phoneAuthCredential) }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun signIn(emailAuthCredential: EmailAuthCredential): Flow<Response<SessionInfo>> =
        runSafe {
            authRepository.signIn(emailAuthCredential.email, emailAuthCredential.password)
                .flatMapConcat { response ->
                    if (response !is Response.Success) return@flatMapConcat flowOf(Response.Empty)
                    getLoggedUserFromFirebase()
                }
        }

    override fun signOut() {
        authRepository.signOut()
    }

    override fun thereIsLoggedUser(): Boolean {
        return authRepository.getCurrentUser() != null
    }

    override fun createNewSystemAccess(requirements: NewAccessRequirements) =
        runSafe { createSystemAccess.execute(requirements) }

    override fun getSessionInfo(): Flow<Response<SessionInfo>> = runSafe {
        getLoggedUserFromFirebase()
    }

    private fun getLoggedUserFromFirebase(): Flow<Response<SessionInfo>> =
        authRepository.getCurrentUser()?.let { fbUser ->
            getLoggedUser.execute(fbUser.uid)
        } ?: flowOf(
            Response.Error(
                NullFirebaseUserException(
                    "Firebase returned a null FirebaseUser."
                )
            )
        )

}