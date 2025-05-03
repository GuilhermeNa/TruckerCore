package com.example.truckercore.model.infrastructure.data_source.firebase.auth

import com.example.truckercore.model.infrastructure.integration.auth.for_api.AuthSource
import com.example.truckercore.model.infrastructure.integration.auth.for_api.exceptions.SessionInactiveException
import com.example.truckercore.model.infrastructure.integration.auth.for_app.requirements.UserProfile
import com.example.truckercore.model.shared.utils.expressions.cancelJob
import com.example.truckercore.model.shared.value_classes.Email
import com.example.truckercore.model.shared.value_classes.Password
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.userProfileChangeRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class FirebaseAuthSource(
    private val auth: FirebaseAuth,
    errorMapper: FirebaseAuthErrorMapper
) : AuthSource(errorMapper) {

    override suspend fun createUserWithEmail(email: Email, password: Password) {
        val task = auth.createUserWithEmailAndPassword(email.value, password.value)
        task.awaitSuccessOrThrow(authError = { errorMapper.creatingUserWithEmail(it) })
    }

    override suspend fun sendEmailVerification() {
        val task = getLoggedUser().sendEmailVerification()
        task.awaitSuccessOrThrow(authError = { errorMapper.sendingEmailVerification(it) })
    }

    override suspend fun updateUserProfile(profile: UserProfile) {
        val updateProfileReq = userProfileChangeRequest {
            displayName = profile.fullName.value
        }
        val task = getLoggedUser().updateProfile(updateProfileReq)
        task.awaitSuccessOrThrow(authError = { errorMapper.updatingProfile(it) })
    }

    override suspend fun signInWithEmail(email: Email, password: Password) {
        val task = auth.signInWithEmailAndPassword(email.value, password.value)
        task.awaitSuccessOrThrow(authError = { errorMapper.signingInWithEmail(it) })
    }

    override suspend fun observeEmailValidation() {
        val fbUser = getLoggedUser()

        suspendCancellableCoroutine { cont ->
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

    override fun signOut() = auth.signOut()

    override fun thereIsLoggedUser(): Boolean = auth.currentUser?.let { true } ?: false

    override fun getUserEmail(): String? {
        return getLoggedUser().email
    }

    override fun isEmailVerified(): Boolean {
        return getLoggedUser().isEmailVerified
    }

    private fun getLoggedUser(): FirebaseUser {
        return auth.currentUser ?: throw SessionInactiveException()
    }

}
