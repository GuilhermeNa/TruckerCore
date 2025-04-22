package com.example.truckercore.model.infrastructure.security.authentication.use_cases

import com.google.firebase.auth.FirebaseUser

/**
 * Represents the result of creating a new user account via email and performing related actions,
 * such as updating the user's profile and sending a verification email.
 *
 * This class is typically used in authentication flows where multiple operations are chained together,
 * and each step may succeed or fail independently.
 *
 * @property firebaseUser The [FirebaseUser] instance returned by Firebase after account creation.
 *                        May be null if the creation failed.
 * @property userCreated Indicates whether the user account was successfully created.
 * @property nameUpdated Indicates whether the display name (or other profile data) was successfully updated.
 * @property emailSent Indicates whether the verification email was successfully sent.
 * @property errors A [Set] of [Exception]s containing all the errors that occurred during the process.
 *                  This allows multiple failure points to be tracked and reported together.
 */
data class NewEmailResult(
    val firebaseUser: FirebaseUser? = null,
    val userCreated: Boolean = false,
    val nameUpdated: Boolean = false,
    val emailSent: Boolean = false,
    val errors: Set<Exception>
)