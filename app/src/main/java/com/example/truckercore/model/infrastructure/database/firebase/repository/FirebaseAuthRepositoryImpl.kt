package com.example.truckercore.model.infrastructure.database.firebase.repository

import com.example.truckercore.model.infrastructure.database.firebase.errors.IncompleteTaskException
import com.example.truckercore.model.shared.utils.sealeds.Response
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthCredential
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

private const val NEW_USER_ERROR_MESSAGE = "Failed on recover new user."

private const val VERIFICATION_ERROR_MESSAGE = "Failed on send verification to registered email."

internal class FirebaseAuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth
) : FirebaseAuthRepository {

    override suspend fun createUserWithEmail(
        email: String, password: String
    ) = suspendCoroutine { continuation ->
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            task.exception?.let { e ->
                continuation.resume(Response.Error(e))
                return@addOnCompleteListener
            }
            task.result.user?.let { fbUser ->
                continuation.resume(Response.Success(fbUser))
            } ?: continuation.resumeWithException(IncompleteTaskException(NEW_USER_ERROR_MESSAGE))
        }
    }

    override suspend fun sendEmailVerification(
        firebaseUser: FirebaseUser
    ): Response<Unit> = suspendCoroutine { continuation ->
        firebaseUser.sendEmailVerification().addOnCompleteListener { task ->
            task.exception?.let { e ->
                continuation.resume(Response.Error(e))
                return@addOnCompleteListener
            }
            if (task.isSuccessful) {
                continuation.resume(Response.Success(Unit))
            } else {
                continuation.resumeWithException(IncompleteTaskException(VERIFICATION_ERROR_MESSAGE))
            }
        }
    }

    override suspend fun createUserWithPhone(credential: PhoneAuthCredential) =
        suspendCoroutine { cont ->
            firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
                task.exception?.let { error ->
                    cont.resume(Response.Error(error))
                    return@addOnCompleteListener
                }
                task.result.user?.let { user ->
                    cont.resume(Response.Success(user.uid))
                } ?: cont.resumeWithException(IncompleteTaskException(NEW_USER_ERROR_MESSAGE))
            }
        }

    override fun signIn(email: String, password: String) = callbackFlow {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                task.exception?.let { error ->
                    this.close(error)
                }

                if (task.isSuccessful) trySend(Response.Success(Unit))
                else close(IncompleteTaskException("Failure when trying to login."))
            }

        awaitClose { this.cancel() }
    }

    override fun signOut() {
        firebaseAuth.signOut()
    }

    override fun getCurrentUser(): FirebaseUser? = firebaseAuth.currentUser

}