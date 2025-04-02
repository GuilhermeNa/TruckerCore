package com.example.truckercore.model.infrastructure.security.authentication.entity

import com.google.firebase.auth.FirebaseUser

/**
 * Data class representing the response after creating a new user via email and sending email verification.
 *
 * This class encapsulates the result of the user creation process and the email verification status.
 * It provides information about whether the user was successfully created, whether the verification email was sent,
 * and any errors that might have occurred during the process.
 *
 * @property user The [FirebaseUser] object for the newly created user. It is null if user creation failed.
 * @property userCreated A boolean flag indicating whether the user was successfully created.
 * @property emailSent A boolean flag indicating whether the email verification was successfully sent.
 * @property _createUserError An [Exception] that occurred during the user creation process, if any.
 * @property sendEmailError An [Exception] that occurred during the email sending process, if any.
 */
data class NewEmailUserResponse(
    val user: FirebaseUser? = null,
    val userCreated: Boolean,
    val emailSent: Boolean,
    private val _createUserError: Exception? = null,
    val sendEmailError: Exception? = null
) {

    val createUserError get() = _createUserError!!

    val userCreatedAndEmailSent = userCreated && emailSent

    val userCreatedAndEmailFailed = userCreated && !emailSent

}
