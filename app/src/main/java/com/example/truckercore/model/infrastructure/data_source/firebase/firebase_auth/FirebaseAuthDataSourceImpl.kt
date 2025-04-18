package com.example.truckercore.model.infrastructure.data_source.firebase.firebase_auth

import com.example.truckercore.model.infrastructure.data_source.firebase.exceptions.IncompleteTaskException
import com.example.truckercore.model.infrastructure.data_source.firebase.expressions.awaitSuccessOrThrow
import com.example.truckercore.model.infrastructure.data_source.firebase.exceptions.NullFirebaseUserException
import com.example.truckercore.model.infrastructure.integration.source_auth.AuthSource
import com.example.truckercore.model.shared.utils.expressions.cancelJob
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FirebaseAuthDataSourceImpl(private val auth: FirebaseAuth) : AuthSource {

    override suspend fun createUserWithEmail(email: String, password: String): FirebaseUser =
        suspendCoroutine { cont ->
            val task = auth.createUserWithEmailAndPassword(email, password)
            task.addOnCompleteListener { taskResult ->
                taskResult.exception?.let { e ->
                    cont.resumeWithException(e)
                    return@addOnCompleteListener
                }
                if (taskResult.isSuccessful) taskResult.result.user?.let { fbUser ->
                    cont.resume(fbUser)
                } ?: cont.resumeWithException(IncompleteTaskException())
            }
        }

    override suspend fun sendEmailVerification() {
        val fbUser = getCurrentUser() ?: throw NullFirebaseUserException()
        val task = fbUser.sendEmailVerification()
        task.awaitSuccessOrThrow()
    }

    override suspend fun updateUserProfile(profile: UserProfileChangeRequest) {
        val fbUser = getCurrentUser() ?: throw NullFirebaseUserException()
        val task = fbUser.updateProfile(profile)
        task.awaitSuccessOrThrow()
    }

    override suspend fun signInWithEmail(email: String, password: String) {
        val task = auth.signInWithEmailAndPassword(email, password)
        task.awaitSuccessOrThrow()
    }

    override fun signOut() = auth.signOut()

    override fun getCurrentUser(): FirebaseUser? = auth.currentUser

    override suspend fun observeEmailValidation() {
        val fbUser = getCurrentUser() ?: throw NullFirebaseUserException()

        suspendCoroutine { cont ->
            // Launches a coroutine tied to the caller's context.
            // This ensures that the job is automatically cancelled with caller context.
            CoroutineScope(cont.context).launch {
                while (isActive) {
                    delay(1000)
                    fbUser.reload()
                    if (fbUser.isEmailVerified) {
                        cont.resume(Unit)

                        // Cancel CoroutineScope Job.
                        // Because the caller context may not be canceled after this completion.
                        cancelJob()
                    }
                }
            }
        }

    }

}
