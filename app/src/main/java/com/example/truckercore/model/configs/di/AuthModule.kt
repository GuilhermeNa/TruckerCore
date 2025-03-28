package com.example.truckercore.model.configs.di

import com.example.truckercore.model.infrastructure.security.authentication.service.AuthService
import com.example.truckercore.model.infrastructure.security.authentication.service.AuthServiceImpl
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.CreateAndVerifyUserEmailUseCase
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.CreateAndVerifyUserEmailUseCaseImpl
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.CreateNewSystemAccessUseCase
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.CreateNewSystemAccessUseCaseImpl
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.GetSessionInfoUseCase
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.GetSessionInfoUseCaseImpl
import org.koin.dsl.module

val authModule = module {
    single<AuthService> { AuthServiceImpl(get(), get(), get(), get(), get()) }
    single<CreateNewSystemAccessUseCase> {
        CreateNewSystemAccessUseCaseImpl(get(), get(), get(), get(), get())
    }
    single<GetSessionInfoUseCase> {
        GetSessionInfoUseCaseImpl(get(), get(), get(), get())
    }
    single<CreateAndVerifyUserEmailUseCase> { CreateAndVerifyUserEmailUseCaseImpl(get()) }
}