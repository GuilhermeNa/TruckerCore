package com.example.truckercore.layers.data.repository.auth

import com.example.truckercore.core.my_lib.classes.Email
import com.example.truckercore.core.my_lib.classes.Name
import com.example.truckercore.core.my_lib.classes.Password
import com.example.truckercore.layers.data.base.outcome.DataOutcome
import com.example.truckercore.layers.data.base.outcome.OperationOutcome
import com.example.truckercore.layers.domain.base.ids.UID

/**
 * Repository interface that defines authentication-related operations used by the application layer.
 */
interface AuthenticationRepository {

    /**
     * Creates a new user account using the given email and password.
     */
    suspend fun createUserWithEmail(email: Email, password: Password): OperationOutcome

    /**
     * Sends an email verification to the currently authenticated user.
     */
    suspend fun sendEmailVerification(): OperationOutcome

    /**
     * Signs in the user with the provided [email] and [password].
     */
    suspend fun signIn(email: Email, password: Password): OperationOutcome

    /**
     * This method can be used to wait for the user's email to be verified after registration.
     */
    suspend fun waitEmailValidation(): OperationOutcome

    /**
     * Sends a password reset email to the given [email] address.
     */
    suspend fun sendPasswordResetEmail(email: Email): OperationOutcome

    /**
     * Signs out the currently authenticated user.
     *
     * This operation is typically synchronous and does not return a result.
     */
    fun signOut()

    /**
     * Checks whether there is a user currently logged in.
     *
     * @return [DataOutcome] containing true if a user is logged in, false otherwise.
     */
    fun thereIsLoggedUser(): DataOutcome<Boolean>

    /**
     * Retrieves the email address of the currently authenticated user.
     *
     * @return [DataOutcome] containing the [Email] of the logged-in user.
     */
    fun getUserEmail(): DataOutcome<Email>

    /**
     * Checks whether the currently authenticated user's email is verified.
     *
     * @return [DataOutcome] containing true if the user's email is verified, false otherwise.
     */
    fun isEmailVerified(): DataOutcome<Boolean>

    /**
     * Checks whether the authenticated user's profile has already been created.
     *
     * @return [DataOutcome] containing true if the user's name is already stored
     * and the profile is considered created, or false otherwise.
     */
    fun isProfileCreated(): DataOutcome<Boolean>

    suspend fun updateName(name: Name): OperationOutcome

    fun getUserName(): DataOutcome<Name>

    /**
     * Retrieves the unique identifier (UID) of the currently authenticated user.
     *
     * @return [DataOutcome] containing the user's [UID].
     */
    fun getUid(): DataOutcome<UID>

}