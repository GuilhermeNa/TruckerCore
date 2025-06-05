package com.example.truckercore.model.configs.di

import com.example.truckercore.model.infrastructure.data_source.datastore.UserPreferencesDataStore
import com.example.truckercore.model.infrastructure.integration.auth.for_app.repository.AuthRepositoryErrorFactory
import com.example.truckercore.model.infrastructure.integration.auth.for_app.repository.AuthenticationRepository
import com.example.truckercore.model.infrastructure.integration.auth.for_app.repository.AuthenticationRepositoryImpl
import com.example.truckercore.model.infrastructure.integration.data.for_app.repository.DataRepository
import com.example.truckercore.model.infrastructure.integration.data.for_app.repository.DataRepositoryErrorFactory
import com.example.truckercore.model.infrastructure.integration.data.for_app.repository.DataRepositoryImpl
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.repository.InstructionRepositoryErrorFactory
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.repository.InstructionExecutorRepository
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.repository.InstructionExecutorRepositoryImpl
import com.example.truckercore.model.infrastructure.integration.preferences.PreferencesRepository
import com.example.truckercore.model.infrastructure.integration.preferences.PreferencesRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoriesModule = module {
    // Data Storage
    single { UserPreferencesDataStore(androidContext()) }
    single<PreferencesRepository> { PreferencesRepositoryImpl(get()) }

    // Data Repository
    single { DataRepositoryErrorFactory() }
    single<DataRepository> { DataRepositoryImpl(get(), get()) }

    // Auth Repository
    single { AuthRepositoryErrorFactory() }
    single<AuthenticationRepository> { AuthenticationRepositoryImpl(get(), get()) }

    // Instruction Repository
    single { InstructionRepositoryErrorFactory() }
    single<InstructionExecutorRepository> { InstructionExecutorRepositoryImpl(get(), get()) }

}