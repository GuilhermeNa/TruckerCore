package com.example.truckercore.model.infrastructure.data_source.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.truckercore.model.infrastructure.integration.preferences.model.RegistrationStep
import com.example.truckercore.model.infrastructure.integration.preferences.model.UserRegistrationStatus
import kotlinx.coroutines.flow.first

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "userPreferences")

private typealias EmailStep = RegistrationStep.Email
private typealias NameStep = RegistrationStep.Name
private typealias EmailVerifiedStep = RegistrationStep.EmailVerified
private typealias PreparedStep = RegistrationStep.Prepared

/**
 * Handles persistent storage of user-related preferences using Jetpack DataStore.
 *
 * This class encapsulates access to user onboarding and registration state,
 * such as whether it is the user's first access or which registration steps
 * have already been completed.
 *
 * The underlying storage uses [DataStore] with [Preferences], scoped to the application context.
 *
 * @property context The application [Context], required for accessing the DataStore.
 */
class UserPreferencesDataStore(private val context: Context) {

    private val isFirstAccess = booleanPreferencesKey(FIRST_ACCESS)
    private val isEmailCreated = booleanPreferencesKey(EMAIL_CREATED)
    private val isNameUpdated = booleanPreferencesKey(NAME_UPDATED)
    private val isEmailVerified = booleanPreferencesKey(EMAIL_VERIFIED)
    private val isAmbientPrepared = booleanPreferencesKey(AMBIENT_PREPARED)

    /**
     * Checks if this is the user's first time opening the app.
     *
     * @return `true` if the user has never accessed the app before, `false` otherwise.
     */
    suspend fun isFirstAccess(): Boolean {
        val preferences = getPreferences()
        return preferences[isFirstAccess] ?: true
    }

    /**
     * Marks that the user has completed their first access to the application.
     */
    suspend fun markFirstAccessComplete() {
        context.dataStore.edit { settings ->
            settings[isFirstAccess] = false
        }
    }

    /**
     * Retrieves the current values stored in the DataStore.
     *
     * @return [Preferences] containing all stored key-value pairs.
     */
    private suspend fun getPreferences(): Preferences {
        return context.dataStore.data.first()
    }

    /**
     * Loads the current status of the user's registration process.
     *
     * This includes all relevant flags representing completed registration steps.
     *
     * @return A [UserRegistrationStatus] object describing the current registration state.
     */
    suspend fun getUserRegistrationStatus(): UserRegistrationStatus {
        val preferences = getPreferences()
        return UserRegistrationStatus(
            emailCreated = preferences[isEmailCreated] ?: false,
            nameUpdated = preferences[isNameUpdated] ?: false,
            emailVerified = preferences[isEmailVerified] ?: false,
            ambientPrepared = preferences[isAmbientPrepared] ?: false
        )
    }

    /**
     * Marks a specific registration step as completed.
     *
     * @param step A [RegistrationStep] indicating which part of the process was completed.
     */
    suspend fun markStepAsCompleted(step: RegistrationStep) {
        val key = when (step) {
            is EmailStep -> isEmailCreated
            is NameStep -> isNameUpdated
            is EmailVerifiedStep -> isEmailVerified
            is PreparedStep -> isAmbientPrepared
        }
        context.dataStore.edit { settings -> settings[key] = true }
    }

    companion object {
        private const val FIRST_ACCESS = "firstAccess"
        private const val EMAIL_CREATED = "emailCreated"
        private const val NAME_UPDATED = "nameUpdated"
        private const val EMAIL_VERIFIED = "emailVerified"
        private const val AMBIENT_PREPARED = "ambientPrepared"
    }

}