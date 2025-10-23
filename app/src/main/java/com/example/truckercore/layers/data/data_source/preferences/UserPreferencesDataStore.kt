package com.example.truckercore.layers.data.data_source.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "userPreferences")

/**
 * Handles persistent storage of user-related preferences using Jetpack DataStore.
 *
 * @property context The application [Context], required for accessing the DataStore.
 */
class UserPreferencesDataStore(private val context: Context) {

    private val isFirstAccess = booleanPreferencesKey(FIRST_ACCESS)
    private val keepLogged = booleanPreferencesKey(KEEP_LOGGED)

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
     * Retrieves the current values stored in the DataStore.
     *
     * @return [Preferences] containing all stored key-value pairs.
     */
    private suspend fun getPreferences(): Preferences {
        return context.dataStore.data.first()
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
     * Checks whether the user has opted to stay logged in.
     *
     * @return `true` if the user should remain logged in, `false` otherwise.
     */
    suspend fun keepLogged(): Boolean {
        val preferences = getPreferences()
        return preferences[keepLogged] ?: true
    }

    /**
     * Sets the user's preference for staying logged in across sessions.
     *
     * @param active `true` to keep the user logged in, `false` to require login next time.
     */
    suspend fun setKeepLogged(active: Boolean) {
        context.dataStore.edit { settings ->
            settings[keepLogged] = active
        }
    }

    companion object {
        private const val FIRST_ACCESS = "firstAccess"
        private const val KEEP_LOGGED = "keepLogged"
    }

}