package com.example.truckercore.model.modules.authentication.manager

import com.example.truckercore._shared.classes.AppResponse
import com.example.truckercore._shared.classes.AppResult
import com.example.truckercore._shared.classes.Email
import com.example.truckercore.model.infrastructure.integration.auth.for_app.data.EmailCredential
import com.example.truckercore.model.modules.authentication.data.UID
import com.example.truckercore.model.modules.authentication.use_cases.interfaces.CreateUserWithEmailUseCase
import com.example.truckercore.model.modules.authentication.use_cases.interfaces.GetUidUseCase
import com.example.truckercore.model.modules.authentication.use_cases.interfaces.GetUserEmailUseCase
import com.example.truckercore.model.modules.authentication.use_cases.interfaces.IsEmailVerifiedUseCase
import com.example.truckercore.model.modules.authentication.use_cases.interfaces.ObserveEmailValidationUseCase
import com.example.truckercore.model.modules.authentication.use_cases.interfaces.ResetEmailUseCase
import com.example.truckercore.model.modules.authentication.use_cases.interfaces.SendVerificationEmailUseCase
import com.example.truckercore.model.modules.authentication.use_cases.interfaces.SignInUseCase
import com.example.truckercore.model.modules.authentication.use_cases.interfaces.SignOutUseCase
import com.example.truckercore.model.modules.authentication.use_cases.interfaces.ThereIsLoggedUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class AuthManagerImpl(
    private val getUserEmail: GetUserEmailUseCase,
    private val isEmailVerifiedUseCase: IsEmailVerifiedUseCase,
    private val createUserWithEmail: CreateUserWithEmailUseCase,
    private val sendVerificationEmail: SendVerificationEmailUseCase,
    private val observeEmailValidation: ObserveEmailValidationUseCase,
    private val thereIsLoggedUserUseCase: ThereIsLoggedUserUseCase,
    private val signInUseCase: SignInUseCase,
    private val signOutUseCase: SignOutUseCase,
    private val resetEmailUseCase: ResetEmailUseCase,
    private val getUidUseCase: GetUidUseCase
) : AuthManager {

    override suspend fun createUserWithEmail(credential: EmailCredential): AppResult<Unit> =
        withContext(Dispatchers.IO) { createUserWithEmail.invoke(credential) }

    override suspend fun sendVerificationEmail(): AppResult<Unit> =
        withContext(Dispatchers.IO) { sendVerificationEmail.invoke() }

    override suspend fun observeEmailValidation(): AppResult<Unit> =
        withContext(Dispatchers.IO) { observeEmailValidation.invoke() }

    override fun thereIsLoggedUser(): Boolean = thereIsLoggedUserUseCase.invoke()

    override fun getUserEmail(): AppResponse<Email> = getUserEmail.invoke()

    override fun isEmailVerified(): AppResult<Boolean> = isEmailVerifiedUseCase.invoke()

    override suspend fun resetPassword(email: Email): AppResult<Unit> =
        withContext(Dispatchers.IO) { resetEmailUseCase(email) }

    override fun getUID(): AppResult<UID> = getUidUseCase()

    override suspend fun signIn(credential: EmailCredential): AppResult<Unit> =
        withContext(Dispatchers.IO) { signInUseCase(credential) }

    override fun signOut() {
        signOutUseCase()
    }

}