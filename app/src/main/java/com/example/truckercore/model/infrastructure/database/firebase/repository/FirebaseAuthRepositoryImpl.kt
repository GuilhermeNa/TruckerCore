package com.example.truckercore.model.infrastructure.database.firebase.repository

import com.example.truckercore.model.infrastructure.security.authentication.errors.AuthErrorFactory
import com.example.truckercore.model.shared.utils.sealeds.AppResponse
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

class FirebaseAuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth,
    private val errFactory: AuthErrorFactory
) : FirebaseAuthRepository {

    override suspend fun createUserWithEmail(
        email: String, password: String
    ): AppResponse<FirebaseUser> = suspendCoroutine { continuation ->
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            task.exception?.let { e ->
                val mErr = errFactory.handleCreateUserWithEmailError(e)
                continuation.resume(mErr)
                return@addOnCompleteListener
            }

            val response = task.result.user?.let { fbUser ->
                AppResponse.Success(fbUser)
            } ?: AppResponse.Empty

            continuation.resume(response)
        }
    }

    override suspend fun sendEmailVerification(
        firebaseUser: FirebaseUser
    ): AppResult<Unit> = suspendCoroutine { continuation ->
        firebaseUser.sendEmailVerification().addOnCompleteListener { task ->
            task.exception?.let { e ->
                val mErr = errFactory.handleSendEmailVerificationError(e)
                continuation.resume(mErr)
                return@addOnCompleteListener
            }

            val response = if (task.isSuccessful) AppResult.Success(Unit)
            else errFactory.handleSendEmailVerificationError()

            continuation.resume(response)
        }
    }

    override suspend fun updateUserProfile(
        fbUser: FirebaseUser,
        profile: UserProfileChangeRequest
    ): AppResult<Unit> = suspendCoroutine { continuation ->
        fbUser.updateProfile(profile).addOnCompleteListener { task ->
            task.exception?.let { e ->
                val mErr = errFactory.handleUpdateUserNameError(e)
                continuation.resume(mErr)
                return@addOnCompleteListener
            }

            val response = if (task.isSuccessful) AppResult.Success(Unit)
            else errFactory.handleUpdateUserNameError()

            continuation.resume(response)
        }
    }

    override fun observeEmailValidation(): Flow<AppResponse<Unit>> = flow {
        while (coroutineContext.isActive) {
            delay(1000)

            val response = getCurrentUser()?.let { fbUser ->
                fbUser.reload()
                if (fbUser.isEmailVerified) AppResponse.Success(Unit)
                else AppResponse.Empty
            } ?: errFactory.handleObservingEmailValidationError()

            emit(response)
        }
    }

    override fun signOut() {
        firebaseAuth.signOut()
    }

    override fun getCurrentUser(): FirebaseUser? = firebaseAuth.currentUser

    override suspend fun signIn(email: String, password: String): AppResult<Unit> =
        suspendCoroutine { continuation ->
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                task.exception?.let { e ->
                    val mErr = errFactory.handleSignInError(e)
                    continuation.resume(mErr)
                    return@addOnCompleteListener
                }

                val result = if (task.isSuccessful) AppResult.Success(Unit)
                else errFactory.handleSignInError()

                continuation.resume(result)
            }
        }

}