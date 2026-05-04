package com.example.truckercore.layers.data_2.repository.impl

import com.example.truckercore.layers.data_2.preferences.UserPreferencesDataStore
import com.example.truckercore.layers.data_2.repository.interfaces.PreferencesRepository

class PreferencesRepositoryImpl(
    private val dataStore: UserPreferencesDataStore
) : PreferencesRepository {

    override suspend fun isFirstAccess(): Boolean = dataStore.isFirstAccess()

    override suspend fun setFirstAccessComplete() {
        dataStore.markFirstAccessComplete()
    }

    override suspend fun keepLogged(): Boolean = dataStore.keepLogged()

    override suspend fun setKeepLogged(active: Boolean) {
        dataStore.setKeepLogged(active)
    }

}