package com.example.truckercore.configs.di

import com.example.truckercore.infrastructure.security.authentication.service.AuthService
import com.example.truckercore.infrastructure.security.authentication.service.AuthServiceImpl
import com.example.truckercore.infrastructure.security.authentication.use_cases.CreateNewSystemAccessUseCase
import com.example.truckercore.infrastructure.security.authentication.use_cases.CreateNewSystemAccessUseCaseImpl
import org.koin.dsl.module

val authModule = module {
    single<AuthService> { AuthServiceImpl(get(), get(), get(), get()) }
    single<CreateNewSystemAccessUseCase> {
        CreateNewSystemAccessUseCaseImpl(get(), get(), get(), get(), get(), get())
    }
}