package com.example.truckercore.layers.data.data_source.auth

import com.example.truckercore.core.error.DomainException
import com.example.truckercore.core.my_lib.classes.Email
import com.example.truckercore.core.my_lib.classes.Name
import com.example.truckercore.core.my_lib.classes.Password
import com.example.truckercore.core.my_lib.files.ONE_SEC
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.userProfileChangeRequest
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await

/**
 * Implementation of [AuthSource] using Firebase Authentication as the backend.
 *
 * This class handles user creation, login, email verification, profile updates,
 * and password reset operations. All Firebase exceptions are mapped to domain-specific
 * exceptions using [AuthSourceErrorMapper].
 */
class AuthSourceImpl(
    private val auth: FirebaseAuth,
    errorMapper: AuthSourceErrorMapper
) : AuthSource(errorMapper) {

    override suspend fun createUserWithEmail(email: Email, password: Password) {
        try {
            auth.createUserWithEmailAndPassword(email.value, password.value).await()
        } catch (e: Exception) {
            throw errorMapper(e)
        }
    }

    override suspend fun sendEmailVerification() {
        try {
            getLoggedUser().sendEmailVerification().await()
        } catch (e: Exception) {
            throw errorMapper(e)
        }
    }

    override suspend fun signInWithEmail(email: Email, password: Password) {
        try {
            auth.signInWithEmailAndPassword(email.value, password.value).await()
        } catch (e: Exception) {
            throw errorMapper(e)
        }
    }

    override suspend fun waitEmailValidation() {
        val user = getLoggedUser()

        while (true) {
            user.reload().await()
            if (user.isEmailVerified) return
            delay(ONE_SEC)
        }
    }

    override suspend fun updateName(name: Name) {
        val user = getLoggedUser()
        val request = userProfileChangeRequest {
            displayName = name.value
        }

        try {
            user.updateProfile(request).await()
        } catch (e: Exception) {
            throw errorMapper(e)
        }
    }

    override fun isNameDefined(): Boolean {
        val name = getLoggedUser().displayName
        return !name.isNullOrBlank()
    }

    override fun hasLoggedUser(): Boolean = auth.currentUser != null

    override fun getUserEmail(): String? = getLoggedUser().email

    override fun getUserName(): String? = getLoggedUser().displayName

    override fun getUid(): String = getLoggedUser().uid

    override fun isEmailVerified(): Boolean = getLoggedUser().isEmailVerified

    private fun getLoggedUser(): FirebaseUser =
        auth.currentUser ?: throw DomainException.UserNotFound()

    override suspend fun sendPasswordResetEmail(email: Email) {
        try {
            auth.sendPasswordResetEmail(email.value).await()
        } catch (e: Exception) {
            throw errorMapper(e)
        }
    }

    override fun signOut() = auth.signOut()

}
