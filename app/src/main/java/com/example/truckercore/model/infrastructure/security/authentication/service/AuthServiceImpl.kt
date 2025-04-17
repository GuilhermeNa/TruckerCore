package com.example.truckercore.model.infrastructure.security.authentication.service

import com.example.truckercore.model.infrastructure.security.authentication.entity.EmailCredential
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.interfaces.CreateUserAndVerifyEmailUseCase
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.interfaces.ObserveEmailValidationUseCase
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.interfaces.SendVerificationEmailUseCase
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.interfaces.ThereIsLoggedUserUseCase
import com.example.truckercore.model.shared.utils.sealeds.AppResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class AuthServiceImpl(
    private val createAndVerifyEmail: CreateUserAndVerifyEmailUseCase,
    private val sendVerificationEmail: SendVerificationEmailUseCase,
    private val observeEmailValidation: ObserveEmailValidationUseCase,
    private val thereIsLoggedUser: ThereIsLoggedUserUseCase
) : AuthService {

    override suspend fun createUserAndVerifyEmail(credential: EmailCredential) =
        withContext(Dispatchers.IO) { createAndVerifyEmail.invoke(credential) }

    override suspend fun sendVerificationEmail(): AppResult<Unit> =
        withContext(Dispatchers.IO) { sendVerificationEmail.invoke() }

    override suspend fun observeEmailValidation(): AppResult<Unit> =
        withContext(Dispatchers.IO) { observeEmailValidation.invoke() }

    override fun thereIsLoggedUser(): Boolean = thereIsLoggedUser.invoke()


    /*
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun signIn(emailAuthCredential: EmailAuthCredential): Flow<AppResponse<SessionInfo>> =
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



    override fun createNewSystemAccess(requirements: NewAccessRequirements) =
        runSafe { createSystemAccess.execute(requirements) }



    override fun getSessionInfo(): Flow<AppResponse<SessionInfo>> = runSafe {
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
        )*/

}