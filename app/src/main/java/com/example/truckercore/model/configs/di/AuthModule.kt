package com.example.truckercore.model.configs.di

import com.example.truckercore.model.infrastructure.integration.auth.for_app.app_errors.AuthenticationAppErrorFactory
import com.example.truckercore.model.infrastructure.security.authentication.service.AuthService
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.implementations.CreateUserAndVerifyEmailUseCaseImpl
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.implementations.ObserveEmailValidationUseCaseImpl
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.implementations.SendVerificationEmailUseCaseImpl
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.implementations.ThereIsLoggedUserUseCaseImpl
import com.example.truckercore.model.infrastructure.security.authentication.service.AuthServiceImpl
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.interfaces.CreateUserAndVerifyEmailUseCase
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.interfaces.CreateNewSystemAccessUseCase
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.interfaces.GetSessionInfoUseCase
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.interfaces.SendVerificationEmailUseCase
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.interfaces.ObserveEmailValidationUseCase
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.interfaces.ThereIsLoggedUserUseCase
import org.koin.dsl.module

val authModule = module {
    single { AuthenticationAppErrorFactory() }
    single<AuthService> { AuthServiceImpl(get(), get(), get(), get()) }
    single<SendVerificationEmailUseCase> {
        SendVerificationEmailUseCaseImpl(
            get()
        )
    }
    single<ObserveEmailValidationUseCase> {
        ObserveEmailValidationUseCaseImpl(
            get()
        )
    }
    single<CreateUserAndVerifyEmailUseCase> {
        CreateUserAndVerifyEmailUseCaseImpl(
            get(),
            get(),
            get(),
            get()
        )
    }
    single<ThereIsLoggedUserUseCase> {
        ThereIsLoggedUserUseCaseImpl(
            get()
        )
    }

}