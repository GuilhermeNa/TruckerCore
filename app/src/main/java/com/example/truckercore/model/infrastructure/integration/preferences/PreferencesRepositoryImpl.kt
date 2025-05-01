package com.example.truckercore.model.infrastructure.integration.preferences

import com.example.truckercore.model.infrastructure.data_source.datastore.UserPreferencesDataStore
import com.example.truckercore.model.infrastructure.integration.preferences.model.RegistrationStep
import com.example.truckercore.model.infrastructure.integration.preferences.model.UserRegistrationStatus

class PreferencesRepositoryImpl(
    private val dataStore: UserPreferencesDataStore
) : PreferencesRepository {

    override suspend fun isFirstAccess(): Boolean = dataStore.isFirstAccess()

    override suspend fun setFirstAccessComplete() = dataStore.markFirstAccessComplete()

    override suspend fun getUserRegistrationStatus(): UserRegistrationStatus =
        dataStore.getUserRegistrationStatus()

    override suspend fun markStepAsCompleted(step: RegistrationStep) =
        dataStore.markStepAsCompleted(step)

}