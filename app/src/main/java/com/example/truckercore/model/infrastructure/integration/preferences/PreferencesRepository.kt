package com.example.truckercore.model.infrastructure.integration.preferences

import com.example.truckercore.model.infrastructure.integration.preferences.model.RegistrationStep
import com.example.truckercore.model.infrastructure.integration.preferences.model.UserRegistrationStatus

/**
 * Interface that defines the contract for interacting with generic user preferences.
 *
 * This repository interface provides methods for checking and updating user preferences related to the app's
 * state, such as first access, registration steps, and other user-specific settings stored in the DataStore.
 */
interface PreferencesRepository {

    /**
     * Checks if this is the user's first time accessing the app.
     *
     * @return `true` if it's the user's first access, `false` otherwise.
     */
    suspend fun isFirstAccess(): Boolean

    /**
     * Marks the user's first access as complete.
     *
     * This updates the internal preferences to reflect that the user has accessed the app at least once.
     */
    suspend fun setFirstAccessComplete()

    /**
     * Retrieves the current status of user preferences.
     *
     * This function returns a [UserRegistrationStatus] object that provides an overview of various user-specific
     * preferences, such as whether the user has completed certain steps or set specific preferences.
     *
     * @return A [UserRegistrationStatus] representing the current status of user preferences.
     */
    suspend fun getUserRegistrationStatus(): UserRegistrationStatus

    /**
     * Marks a specific preference or setting as completed or enabled.
     *
     * This function updates a particular preference, such as marking a registration step as completed or enabling
     * a specific user setting.
     *
     * @param step A [RegistrationStep] indicating which preference or setting has been updated.
     */
    suspend fun markStepAsCompleted(step: RegistrationStep)

}