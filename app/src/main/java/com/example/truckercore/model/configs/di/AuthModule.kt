package com.example.truckercore.model.configs.di

import com.example.truckercore.model.infrastructure.security.authentication.errors.AuthErrorFactory
import com.example.truckercore.model.infrastructure.security.authentication.service.AuthService
import com.example.truckercore.model.infrastructure.security.authentication.service.AuthServiceImpl
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.CreateUserAndVerifyEmailUseCase
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.CreateUserAndVerifyEmailUseCaseImpl
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.CreateNewSystemAccessUseCase
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.CreateNewSystemAccessUseCaseImpl
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.GetSessionInfoUseCase
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.GetSessionInfoUseCaseImpl
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.SendVerificationEmailUseCase
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.SendVerificationEmailUseCaseImpl
import org.koin.dsl.module

val authModule = module {
    single { AuthErrorFactory }
    single<AuthService> { AuthServiceImpl(get(), get(), get(), get(), get(), get()) }
    single<CreateNewSystemAccessUseCase> {
        CreateNewSystemAccessUseCaseImpl(get(), get(), get(), get(), get())
    }
    single<GetSessionInfoUseCase> {
        GetSessionInfoUseCaseImpl(get(), get(), get(), get())
    }
    single<SendVerificationEmailUseCase> { SendVerificationEmailUseCaseImpl(get()) }

    single<CreateUserAndVerifyEmailUseCase> { CreateUserAndVerifyEmailUseCaseImpl(get(), get(), get(), get()) }

}