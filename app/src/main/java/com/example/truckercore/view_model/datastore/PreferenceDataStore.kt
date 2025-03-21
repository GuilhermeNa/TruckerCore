package com.example.truckercore.view_model.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

class PreferenceDataStore private constructor() {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    private val isFirstAccess = booleanPreferencesKey("firstAccess")

    //----------------------------------------------------------------------------------------------

    suspend fun getFirstAccessStatus(context: Context): Boolean {
        val preferences = context.dataStore.data.first()
        return preferences[isFirstAccess] ?: true
    }

    suspend fun setAppAlreadyAccessed(context: Context) {
        context.dataStore.edit { settings ->
            settings[isFirstAccess] = false
        }
    }

    companion object {
        @Volatile
        private var instance: PreferenceDataStore? = null

        fun getInstance(): PreferenceDataStore =
            instance ?: synchronized(this) {
                instance ?: PreferenceDataStore().also { instance = it }
            }
    }

}