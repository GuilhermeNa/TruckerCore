package com.example.truckercore.model.modules.authentication.service

import com.example.truckercore.model.infrastructure.integration.auth.for_app.requirements.EmailCredential
import com.example.truckercore.model.infrastructure.integration.auth.for_app.requirements.UserProfile
import com.example.truckercore.model.modules.authentication.use_cases.interfaces.CreateUserWithEmailUseCase
import com.example.truckercore.model.modules.authentication.use_cases.interfaces.GetUserEmailUseCase
import com.example.truckercore.model.modules.authentication.use_cases.interfaces.IsEmailVerifiedUseCase
import com.example.truckercore.model.modules.authentication.use_cases.interfaces.ObserveEmailValidationUseCase
import com.example.truckercore.model.modules.authentication.use_cases.interfaces.SendVerificationEmailUseCase
import com.example.truckercore.model.modules.authentication.use_cases.interfaces.ThereIsLoggedUserUseCase
import com.example.truckercore.model.modules.authentication.use_cases.interfaces.UpdateUserProfileUseCase
import com.example.truckercore.model.shared.utils.sealeds.AppResult
import com.example.truckercore._utils.classes.Email
import com.example.truckercore.model.modules.authentication.use_cases.interfaces.SignUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class AuthServiceImpl(
    private val updateUserProfile: UpdateUserProfileUseCase,
    private val getUserEmail: GetUserEmailUseCase,
    private val isEmailVerified: IsEmailVerifiedUseCase,
    private val createUserWithEmail: CreateUserWithEmailUseCase,
    private val sendVerificationEmail: SendVerificationEmailUseCase,
    private val observeEmailValidation: ObserveEmailValidationUseCase,
    private val thereIsLoggedUser: ThereIsLoggedUserUseCase,
    private val sign: SignUseCase
) : AuthService {

    override suspend fun createUserWithEmail(credential: EmailCredential): AppResult<Unit> =
        withContext(Dispatchers.IO) { createUserWithEmail.invoke(credential) }

    override suspend fun sendVerificationEmail(): AppResult<Unit> =
        withContext(Dispatchers.IO) { sendVerificationEmail.invoke() }

    override suspend fun observeEmailValidation(): AppResult<Unit> =
        withContext(Dispatchers.IO) { observeEmailValidation.invoke() }

    override fun thereIsLoggedUser(): AppResult<Boolean> = thereIsLoggedUser.invoke()

    override suspend fun updateUserName(userProfile: UserProfile): AppResult<Unit> =
        withContext(Dispatchers.IO) { updateUserProfile.invoke(userProfile) }

    override fun getUserEmail(): AppResult<Email> = getUserEmail.invoke()

    override fun isEmailVerified(): AppResult<Boolean> = isEmailVerified.invoke()

    override fun signOut() {
        sign.signOut()
    }

    override suspend fun signIn(credential: EmailCredential) =
        sign.signIn(credential)


}