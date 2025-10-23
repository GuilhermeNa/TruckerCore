package com.example.truckercore.layers.data.repository.auth

import com.example.truckercore.core.my_lib.classes.Email
import com.example.truckercore.core.my_lib.classes.Password
import com.example.truckercore.layers.data.base.outcome.DataOutcome
import com.example.truckercore.layers.data.base.outcome.OperationOutcome
import com.example.truckercore.layers.data.data_source.auth.AuthSource
import com.example.truckercore.layers.domain.base.ids.UID

class AuthenticationRepositoryImpl(private val authSource: AuthSource) : AuthenticationRepository {

    override suspend fun createUserWithEmail(email: Email, password: Password): OperationOutcome =
        runSafeOperation { authSource.createUserWithEmail(email, password) }

    override suspend fun sendEmailVerification(): OperationOutcome =
        runSafeOperation { authSource.sendEmailVerification() }

    override suspend fun observeEmailValidation(): OperationOutcome =
        runSafeOperation { authSource.observeEmailValidation() }

    override suspend fun signIn(email: Email, password: Password): OperationOutcome =
        runSafeOperation { authSource.signInWithEmail(email, password) }

    override fun signOut() {
        authSource.signOut()
    }

    override fun thereIsLoggedUser(): DataOutcome<Boolean> =
        DataOutcome.Success(authSource.thereIsLoggedUser())

    override fun getUserEmail(): DataOutcome<Email> =
        runSafeSearch {
            val email = authSource.getUserEmail()
            email?.let { Email.from(it) }
        }

    override fun isEmailVerified(): DataOutcome<Boolean> =
        runSafeSearch { authSource.isEmailVerified() }

    override suspend fun sendPasswordResetEmail(email: Email): OperationOutcome =
        runSafeOperation { authSource.sendPasswordResetEmail(email) }

    override fun getUid(): DataOutcome<UID> =
        runSafeSearch { UID(authSource.getUid()) }

}