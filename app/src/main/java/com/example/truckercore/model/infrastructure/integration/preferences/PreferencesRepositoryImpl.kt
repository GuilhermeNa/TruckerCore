package com.example.truckercore.model.infrastructure.integration.preferences

import com.example.truckercore.model.infrastructure.data_source.datastore.UserPreferencesDataStore

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