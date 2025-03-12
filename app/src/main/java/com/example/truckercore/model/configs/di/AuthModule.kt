package com.example.truckercore.model.configs.di

import com.example.truckercore.model.infrastructure.security.authentication.service.AuthService
import com.example.truckercore.model.infrastructure.security.authentication.service.AuthServiceImpl
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.CreateNewSystemAccessUseCase
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.CreateNewSystemAccessUseCaseImpl
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.GetLoggedUserDetailsUseCase
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.GetLoggedUserDetailsUseCaseImpl
import org.koin.dsl.module

val authModule = module {
    single<AuthService> { AuthServiceImpl(get(), get(), get(), get()) }
    single<CreateNewSystemAccessUseCase> {
        CreateNewSystemAccessUseCaseImpl(get(), get(), get(), get(), get())
    }
    single<GetLoggedUserDetailsUseCase> {
        GetLoggedUserDetailsUseCaseImpl(get(), get())
    }
}