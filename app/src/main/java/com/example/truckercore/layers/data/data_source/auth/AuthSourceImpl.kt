package com.example.truckercore.layers.data.data_source.auth

import com.example.truckercore.core.error.DomainException
import com.example.truckercore.core.my_lib.classes.Email
import com.example.truckercore.core.my_lib.classes.Name
import com.example.truckercore.core.my_lib.classes.Password
import com.example.truckercore.core.my_lib.expressions.cancelJob
import com.example.truckercore.layers.data.data_source.auth.expressions.awaitSuccessOrThrow
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.userProfileChangeRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class AuthSourceImpl(
    private val auth: FirebaseAuth,
    errorMapper: AuthSourceErrorMapper
) : AuthSource(errorMapper) {

    override suspend fun createUserWithEmail(email: Email, password: Password) {
        val task = auth.createUserWithEmailAndPassword(email.value, password.value)
        task.awaitSuccessOrThrow { errorMapper(it) }
    }

    override suspend fun sendEmailVerification() {
        val task = getLoggedUser().sendEmailVerification()
        task.awaitSuccessOrThrow { errorMapper(it) }
    }

    override suspend fun signInWithEmail(email: Email, password: Password) {
        val task = auth.signInWithEmailAndPassword(email.value, password.value)
        task.awaitSuccessOrThrow { errorMapper(it) }
    }

    override suspend fun observeEmailValidation(refreshTime: Long) {
        val fbUser = getLoggedUser()

        suspendCancellableCoroutine { cont ->
            // Launches a coroutine tied to the caller's context.
            // This ensures that the job is automatically cancelled with caller context.
            CoroutineScope(cont.context).launch {
                while (isActive) {
                    delay(refreshTime)
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

    override fun signOut() = auth.signOut()

    override fun updateName(name: Name) {
        TODO("Not yet implemented")
        val req = userProfileChangeRequest {
            displayName = ""

        }
    }


    override fun isNameDefined(): Boolean {
        TODO("Not yet implemented")
        auth.currentUser.updateProfile()
    }

    override fun hasLoggedUser(): Boolean = auth.currentUser?.let { true } ?: false

    override fun getUserEmail(): String? {
        return getLoggedUser().email
    }

    override fun getUid(): String {
        return getLoggedUser().uid
    }

    override fun isEmailVerified(): Boolean {
        return getLoggedUser().isEmailVerified
    }

    private fun getLoggedUser(): FirebaseUser {


        return auth.currentUser ?: throw DomainException.UserNotFound()
    }

    override suspend fun sendPasswordResetEmail(email: Email) {
        val task = auth.sendPasswordResetEmail(email.value)
        task.awaitSuccessOrThrow { errorMapper(it) }
    }

}
