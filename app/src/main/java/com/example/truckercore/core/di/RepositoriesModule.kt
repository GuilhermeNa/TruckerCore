package com.example.truckercore.core.di

import com.example.truckercore.layers.data.data_source.preferences.UserPreferencesDataStore
import com.example.truckercore.layers.data.repository.auth.AuthenticationRepository
import com.example.truckercore.layers.data.repository.auth.AuthenticationRepositoryImpl
import com.example.truckercore.layers.data.repository.data.DataRepository
import com.example.truckercore.layers.data.repository.data.DataRepositoryImpl
import com.example.truckercore.layers.data.repository.preferences.PreferencesRepository
import com.example.truckercore.layers.data.repository.preferences.PreferencesRepositoryImpl
import com.example.truckercore.layers.data.repository.instruction.InstructionRepository
import com.example.truckercore.layers.data.repository.instruction.InstructionRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoriesModule = module {
    // Data Storage
    single { UserPreferencesDataStore(androidContext()) }
    single<PreferencesRepository> { PreferencesRepositoryImpl(get()) }

    // Auth Repository
    single<AuthenticationRepository> { AuthenticationRepositoryImpl(get()) }

    // Data Repository
    single<DataRepository> { DataRepositoryImpl(get()) }

    // Instruction Repository
    single<InstructionRepository> { InstructionRepositoryImpl(get()) }

}