package com.example.truckercore.layers.data.repository.preferences

import com.example.truckercore.layers.data.data_source.preferences.UserPreferencesDataStore

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