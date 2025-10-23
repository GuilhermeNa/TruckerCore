package com.example.truckercore.core.di

import com.example.truckercore.data.infrastructure.data_source.preferences.UserPreferencesDataStore
import com.example.truckercore.data.infrastructure.repository.auth.error_factory.AuthRepositoryErrorFactory
import com.example.truckercore.data.infrastructure.repository.auth.contracts.AuthenticationRepository
import com.example.truckercore.data.infrastructure.repository.auth.impl.AuthenticationRepositoryImpl
import com.example.truckercore.data.infrastructure.repository.data.contracts.DataRepository
import com.example.truckercore.data.infrastructure.repository.data.error_factory.DataRepositoryErrorFactory
import com.example.truckercore.data.infrastructure.repository.data.impl.DataRepositoryImpl
import com.example.truckercore.data.infrastructure.repository.writer.error_factory.InstructionRepositoryErrorFactory
import com.example.truckercore.data.infrastructure.repository.writer.contracts.InstructionExecutorRepository
import com.example.truckercore.data.infrastructure.repository.writer.impl.InstructionExecutorRepositoryImpl
import com.example.truckercore.data.infrastructure.repository.preferences.contracts.PreferencesRepository
import com.example.truckercore.data.infrastructure.repository.preferences.impl.PreferencesRepositoryImpl
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