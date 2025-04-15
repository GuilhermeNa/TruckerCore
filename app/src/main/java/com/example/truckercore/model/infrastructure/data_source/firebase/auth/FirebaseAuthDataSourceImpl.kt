package com.example.truckercore.model.infrastructure.data_source.firebase.auth

import com.example.truckercore.model.infrastructure.data_source.firebase.exceptions.IncompleteTaskException
import com.example.truckercore.model.infrastructure.security.authentication.exceptions.NullFirebaseUserException
import com.example.truckercore.model.shared.utils.expressions.logInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FirebaseAuthDataSourceImpl(
    private val auth: FirebaseAuth,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : FirebaseAuthDataSource {

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
                } ?: cont.resumeWithException(NullFirebaseUserException())
            }
        }

    override suspend fun sendEmailVerification(): Unit = suspendCoroutine { cont ->
        val fbUser = getCurrentUser()
        if (fbUser == null) {
            cont.resumeWithException(NullFirebaseUserException())
            return@suspendCoroutine
        }

        val task = fbUser.sendEmailVerification()
        task.addOnCompleteListener { taskResult ->
            taskResult.exception?.let { e ->
                cont.resumeWithException(e)
                return@addOnCompleteListener
            }
            if (task.isSuccessful) cont.resume(Unit)
            else cont.resumeWithException(IncompleteTaskException())
        }

    }

    override suspend fun updateUserProfile(profile: UserProfileChangeRequest): Unit =
        suspendCoroutine { cont ->
            val fbUser = getCurrentUser()
            if (fbUser == null) {
                cont.resumeWithException(NullFirebaseUserException())
                return@suspendCoroutine
            }

            val task = fbUser.updateProfile(profile)
            task.addOnCompleteListener { taskResult ->
                taskResult.exception?.let { e ->
                    cont.resumeWithException(e)
                    return@addOnCompleteListener
                }
                if (taskResult.isSuccessful) cont.resume(Unit)
                else cont.resumeWithException(IncompleteTaskException())
            }

        }

    override suspend fun signInWithEmail(email: String, password: String): Unit =
        suspendCoroutine { cont ->
            val task = auth.signInWithEmailAndPassword(email, password)
            task.addOnCompleteListener { taskResult ->
                taskResult.exception?.let { e ->
                    cont.resumeWithException(e)
                    return@addOnCompleteListener
                }
                if (taskResult.isSuccessful) cont.resume(Unit)
                else cont.resumeWithException(IncompleteTaskException())
            }
        }

    override fun signOut() = auth.signOut()

    override fun getCurrentUser(): FirebaseUser? = auth.currentUser

    override suspend fun observeEmailValidation(): Unit = suspendCancellableCoroutine { cont ->
        val fbUser = getCurrentUser()
        if (fbUser == null) {
            cont.resumeWithException(NullFirebaseUserException())
            return@suspendCancellableCoroutine
        }

        val job = CoroutineScope(dispatcher).launch {
            while (isActive) {
                delay(1000)
                fbUser.reload()

                if (fbUser.isEmailVerified) {
                    cont.resume(Unit)
                    logInfo("FirebaseAuthDataSourceImpl: Loop rodando")
                }
            }
        }

        cont.invokeOnCancellation { job.cancel() }
    }

}
