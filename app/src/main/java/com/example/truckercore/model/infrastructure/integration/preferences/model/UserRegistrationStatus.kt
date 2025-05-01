package com.example.truckercore.model.infrastructure.integration.preferences.model

/**
 * Holds the current registration progress state for a user.
 *
 * This data class represents the status of each step in the user registration process.
 * It is used to determine whether the user has completed all necessary steps for registration.
 * The user will only be allowed to log in to the system once all steps are completed.
 *
 * @property emailCreated Whether the user has created or provided an email address.
 * @property nameUpdated Whether the user's name has been provided or updated.
 * @property emailVerified Whether the user's email address has been successfully verified.
 * @property ambientPrepared Whether the domain-specific environment has been set up for the user.
 */
data class UserRegistrationStatus(
    val emailCreated: Boolean,
    val nameUpdated: Boolean,
    val emailVerified: Boolean,
    val ambientPrepared: Boolean
) {

    /**
     * Checks if the user has completed all registration steps.
     *
     * This function evaluates whether the user has completed all the required steps for registration.
     * The user will only be able to log in if all steps are marked as completed.
     *
     * @return `true` if the user has completed all steps, `false` otherwise.
     */
    fun isRegistrationComplete(): Boolean {
        return emailCreated && nameUpdated && emailVerified && ambientPrepared
    }

}
