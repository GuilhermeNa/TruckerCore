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

    private val isFirstAppAccess = booleanPreferencesKey("firstAccess")

    suspend fun getIsFirstAppAccess(context: Context): Boolean {
        val preferences = context.dataStore.data.first()
        return preferences[isFirstAppAccess] ?: false
    }

    suspend fun setIsFirstAppAccess(context: Context, value: Boolean) {
        context.dataStore.edit { settings ->
            settings[isFirstAppAccess] = value
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