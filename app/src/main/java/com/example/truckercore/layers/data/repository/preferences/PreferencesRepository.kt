package com.example.truckercore.layers.data.repository.preferences

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
     * Checks whether the user has chosen to remain logged in across sessions.
     *
     * @return `true` if the user should be kept logged in, `false` otherwise.
     */
    suspend fun keepLogged(): Boolean

    /**
     * Sets the user's preference for being kept logged in between sessions.
     *
     * @param active `true` to keep the user logged in, `false` to log out after the session ends.
     */
    suspend fun setKeepLogged(active: Boolean)

}