package com.example.truckercore.layers.data.repository.auth

import com.example.truckercore.core.error.DataException
import com.example.truckercore.core.error.core.AppException
import com.example.truckercore.core.my_lib.classes.Email
import com.example.truckercore.core.my_lib.classes.Name
import com.example.truckercore.core.my_lib.classes.Password
import com.example.truckercore.layers.data.base.outcome.DataOutcome
import com.example.truckercore.layers.data.base.outcome.OperationOutcome
import com.example.truckercore.layers.data.data_source.auth.AuthSource
import com.example.truckercore.layers.domain.base.ids.UID

/**
 * Implementation of [AuthenticationRepository] that delegates authentication operations
 * to an underlying [AuthSource].
 *
 * This class provides a safe and standardized way to perform authentication operations
 * by:
 *  - Wrapping suspending operations in [OperationOutcome] to indicate success or failure.
 *  - Wrapping synchronous or query-like operations in [DataOutcome] to handle empty, success, or failure states.
 *  - Converting any unexpected exceptions into [DataException.Unknown] with a default error message.
 *
 * All domain-specific exceptions from [AuthSource] are preserved when possible.
 */
class AuthenticationRepositoryImpl(private val authSource: AuthSource) : AuthenticationRepository {

    override suspend fun createUserWithEmail(email: Email, password: Password): OperationOutcome =
        runSafeOperation { authSource.createUserWithEmail(email, password) }

    override suspend fun sendEmailVerification(): OperationOutcome =
        runSafeOperation { authSource.sendEmailVerification() }

    override suspend fun waitEmailValidation(): OperationOutcome =
        runSafeOperation { authSource.waitEmailValidation() }

    override suspend fun signIn(email: Email, password: Password): OperationOutcome =
        runSafeOperation { authSource.signInWithEmail(email, password) }

    override fun signOut() {
        authSource.signOut()
    }

    override fun thereIsLoggedUser(): DataOutcome<Boolean> =
        DataOutcome.Success(authSource.hasLoggedUser())

    override fun getUserEmail(): DataOutcome<Email> = runSafeSearch {
        val email = authSource.getUserEmail()
        email?.let { Email.from(it) }
    }

    override fun isEmailVerified(): DataOutcome<Boolean> =
        runSafeSearch { authSource.isEmailVerified() }

    override fun isProfileCreated(): DataOutcome<Boolean> =
        runSafeSearch { authSource.isNameDefined() }

    override suspend fun updateName(name: Name): OperationOutcome = runSafeOperation {
        authSource.updateName(name)
    }

    override fun getUserName(): DataOutcome<Name> = runSafeSearch {
        val name = authSource.getUserName()
        name?.let { Name.from(name) }
    }

    override suspend fun sendPasswordResetEmail(email: Email): OperationOutcome =
        runSafeOperation { authSource.sendPasswordResetEmail(email) }

    override fun getUid(): DataOutcome<UID> =
        runSafeSearch { UID(authSource.getUid()) }

    //----------------------------------------------------------------------------------------------
    // Helpers
    //----------------------------------------------------------------------------------------------
    /**
     * Executes a suspending operation safely, capturing any exceptions and
     * wrapping the result in an [OperationOutcome].
     *
     * - If the operation completes successfully, returns [OperationOutcome.Completed].
     * - If a known [AppException] is thrown, returns [OperationOutcome.Failure] with the exception.
     * - If an unknown exception is thrown, wraps it in [DataException.Unknown] and returns failure.
     */
    private suspend fun runSafeOperation(block: suspend () -> Unit): OperationOutcome =
        try {
            block()
            OperationOutcome.Completed
        } catch (e: AppException) {
            OperationOutcome.Failure(e)
        } catch (e: Exception) {
            OperationOutcome.Failure(DataException.Unknown(DEFAULT_ERROR_MSG, e))
        }

    /**
     * Executes a synchronous or query-like operation safely, capturing any exceptions
     * and wrapping the result in a [DataOutcome].
     *
     * - If the block returns a non-null value, returns [DataOutcome.Success].
     * - If the block returns null, returns [DataOutcome.Empty].
     * - If a known [AppException] is thrown, returns [DataOutcome.Failure] with the exception.
     * - If an unknown exception is thrown, wraps it in [DataException.Unknown] and returns failure.
     */
    private fun <T> runSafeSearch(block: () -> T?): DataOutcome<T> =
        try {
            val result = block()

            if (result != null) DataOutcome.Success(result)
            else DataOutcome.Empty

        } catch (e: AppException) {
            DataOutcome.Failure(e)
        } catch (e: Exception) {
            DataOutcome.Failure(DataException.Unknown(DEFAULT_ERROR_MSG, e))
        }

    companion object {
        private const val DEFAULT_ERROR_MSG = "An unknown error occurred in Auth Repository."
    }

}