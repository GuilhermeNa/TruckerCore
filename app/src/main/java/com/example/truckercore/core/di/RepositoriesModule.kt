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
import com.example.truckercore.layers.data_2.repository.impl.AccessRepositoryImpl
import com.example.truckercore.layers.data_2.repository.impl.AdminRepositoryImpl
import com.example.truckercore.layers.data_2.repository.impl.CompanyRepositoryImpl
import com.example.truckercore.layers.data_2.repository.impl.DriverRepositoryImpl
import com.example.truckercore.layers.data_2.repository.impl.UserRepositoryImpl
import com.example.truckercore.layers.data_2.repository.interfaces.AccessRepository
import com.example.truckercore.layers.data_2.repository.interfaces.AdminRepository
import com.example.truckercore.layers.data_2.repository.interfaces.CompanyRepository
import com.example.truckercore.layers.data_2.repository.interfaces.DriverRepository
import com.example.truckercore.layers.data_2.repository.interfaces.UserRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoriesModule = module {
    // Data Storage
    single { UserPreferencesDataStore(androidContext()) }
    single<PreferencesRepository> { PreferencesRepositoryImpl(get()) }

    // Data 2 Repositories
    single<AccessRepository> { AccessRepositoryImpl(get()) }
    single<AdminRepository> { AdminRepositoryImpl(get()) }
    single<DriverRepository> { DriverRepositoryImpl(get()) }
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<CompanyRepository> { CompanyRepositoryImpl(get()) }


    // Auth Repository
    single<AuthenticationRepository> { AuthenticationRepositoryImpl(get()) }

    // Data Repository
    single<DataRepository> { DataRepositoryImpl(get()) }

    // Instruction Repository
    single<InstructionRepository> { InstructionRepositoryImpl(get()) }

}