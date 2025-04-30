package com.example.truckercore.model.configs.di

import com.example.truckercore.model.infrastructure.integration.auth.for_app.repository.AuthenticationRepository
import com.example.truckercore.model.infrastructure.integration.auth.for_app.repository.AuthenticationRepositoryImpl
import org.koin.dsl.module

val repositoriesModule = module {
    single<AuthenticationRepository> { AuthenticationRepositoryImpl(get(), get()) }
}