package com.example.truckercore.view_model.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class PreferenceDataStore private constructor() {

    //----------------------------------------------------------------------------------------------
    // First user access
    //----------------------------------------------------------------------------------------------
    private val isFirstAccess = booleanPreferencesKey(FIRST_ACCESS)

    suspend fun getFirstAccessStatus(context: Context): Boolean {
        val preferences = context.dataStore.data.first()
        return preferences[isFirstAccess] ?: true
    }

    suspend fun setAppAlreadyAccessed(context: Context) {
        context.dataStore.edit { settings ->
            settings[isFirstAccess] = false
        }
    }

    //----------------------------------------------------------------------------------------------
    //
    //----------------------------------------------------------------------------------------------

    companion object {
        private const val FIRST_ACCESS = "firstAccess"

        @Volatile
        private var instance: PreferenceDataStore? = null

        fun getInstance(): PreferenceDataStore =
            instance ?: synchronized(this) {
                instance ?: PreferenceDataStore().also { instance = it }
            }
    }

}