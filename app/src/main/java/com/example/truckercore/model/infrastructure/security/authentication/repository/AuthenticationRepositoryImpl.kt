package com.example.truckercore.model.infrastructure.security.authentication.repository

import com.example.truckercore.model.infrastructure.security.authentication.exceptions.NullFirebaseUserException
import com.example.truckercore.model.infrastructure.security.authentication.app_errors.AuthenticationAppErrorFactory
import com.example.truckercore.model.shared.utils.sealeds.AppResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import kotlin.coroutines.coroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AuthenticationRepositoryImpl(
    private val firebaseAuth: FirebaseAuth,
    private val appErrorFactory: AuthenticationAppErrorFactory
) : AuthenticationRepository {

    override suspend fun createUserWithEmail(
        email: String, password: String
    ): AppResult<FirebaseUser> = suspendCoroutine { continuation ->
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            task.exception?.let { e ->
                val appError = appErrorFactory.handleCreateUserWithEmailError(e)
                continuation.resume(AppResult.Error(appError))
                return@addOnCompleteListener
            }

            val response = task.result.user?.let { fbUser ->
                AppResult.Success(fbUser)
            } ?: AppResult.Error(appErrorFactory.handleCreateUserWithEmailError())

            continuation.resume(response)
        }
    }

    override suspend fun sendEmailVerification(): AppResult<Unit> =
        suspendCoroutine { continuation ->
            val fbUser = firebaseAuth.currentUser
            if (fbUser == null) {
                val appError = appErrorFactory.handleSendEmailVerificationError(NullFirebaseUserException())
                continuation.resume(AppResult.Error(appError))
                return@suspendCoroutine
            }

            fbUser.sendEmailVerification().addOnCompleteListener { task ->
                task.exception?.let { e ->
                    val appError = appErrorFactory.handleSendEmailVerificationError(e)
                    continuation.resume(AppResult.Error(appError))
                    return@addOnCompleteListener
                }

                val response = if (task.isSuccessful) AppResult.Success(Unit)
                else AppResult.Error(appErrorFactory.handleSendEmailVerificationError())

                continuation.resume(response)
            }

        }

    override suspend fun updateUserProfile(profile: UserProfileChangeRequest): AppResult<Unit> =
        suspendCoroutine { continuation ->
            val fbUser = firebaseAuth.currentUser
            if (fbUser == null) {
                val appError = appErrorFactory.handleUpdateUserNameError(NullFirebaseUserException())
                continuation.resume(AppResult.Error(appError))
                return@suspendCoroutine
            }

            fbUser.updateProfile(profile).addOnCompleteListener { task ->
                task.exception?.let { e ->
                    val appError = appErrorFactory.handleUpdateUserNameError(e)
                    continuation.resume(AppResult.Error(appError))
                    return@addOnCompleteListener
                }

                val response = if (task.isSuccessful) AppResult.Success(Unit)
                else AppResult.Error(appErrorFactory.handleUpdateUserNameError())

                continuation.resume(response)
            }
        }

    override fun observeEmailValidation(): Flow<AppResult<Unit>> = flow {
        while (coroutineContext.isActive) {
            delay(1000)

            val result: AppResult<Unit>? = getCurrentUser()?.let { fbUser ->
                fbUser.reload()
                if (fbUser.isEmailVerified) AppResult.Success(Unit)
                else null
            } ?: AppResult.Error(appErrorFactory.handleObservingEmailValidationError())

            result?.let { emit(it) }

        }
    }

    override suspend fun signIn(email: String, password: String): AppResult<Unit> =
        suspendCoroutine { continuation ->
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                task.exception?.let { e ->
                    val appError = appErrorFactory.handleSignInError(e)
                    continuation.resume(AppResult.Error(appError))
                    return@addOnCompleteListener
                }

                val result = if (task.isSuccessful) AppResult.Success(Unit)
                else AppResult.Error(appErrorFactory.handleSignInError())

                continuation.resume(result)
            }
        }

    override fun signOut() {
        firebaseAuth.signOut()
    }

    override fun getCurrentUser(): FirebaseUser? = firebaseAuth.currentUser

}