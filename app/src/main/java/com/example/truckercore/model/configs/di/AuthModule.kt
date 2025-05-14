package com.example.truckercore.model.configs.di

import com.example.truckercore.model.infrastructure.integration.auth.for_app.repository.AuthRepositoryErrorFactory
import com.example.truckercore.model.modules.authentication.manager.AuthManager
import com.example.truckercore.model.modules.authentication.manager.AuthManagerImpl
import com.example.truckercore.model.modules.authentication.use_cases.implementations.CreateUserWithEmailUseCaseImpl
import com.example.truckercore.model.modules.authentication.use_cases.implementations.GetUserEmailUseCaseImpl
import com.example.truckercore.model.modules.authentication.use_cases.implementations.IsEmailVerifiedUseCaseImpl
import com.example.truckercore.model.modules.authentication.use_cases.implementations.ObserveEmailValidationUseCaseImpl
import com.example.truckercore.model.modules.authentication.use_cases.implementations.ResetEmailUseCaseImpl
import com.example.truckercore.model.modules.authentication.use_cases.implementations.SendVerificationEmailUseCaseImpl
import com.example.truckercore.model.modules.authentication.use_cases.implementations.SignInUseCaseImpl
import com.example.truckercore.model.modules.authentication.use_cases.implementations.SignOutUseCaseImpl
import com.example.truckercore.model.modules.authentication.use_cases.implementations.ThereIsLoggedUserUseCaseImpl
import com.example.truckercore.model.modules.authentication.use_cases.interfaces.CreateUserWithEmailUseCase
import com.example.truckercore.model.modules.authentication.use_cases.interfaces.GetUserEmailUseCase
import com.example.truckercore.model.modules.authentication.use_cases.interfaces.IsEmailVerifiedUseCase
import com.example.truckercore.model.modules.authentication.use_cases.interfaces.ObserveEmailValidationUseCase
import com.example.truckercore.model.modules.authentication.use_cases.interfaces.ResetEmailUseCase
import com.example.truckercore.model.modules.authentication.use_cases.interfaces.SendVerificationEmailUseCase
import com.example.truckercore.model.modules.authentication.use_cases.interfaces.SignInUseCase
import com.example.truckercore.model.modules.authentication.use_cases.interfaces.SignOutUseCase
import com.example.truckercore.model.modules.authentication.use_cases.interfaces.ThereIsLoggedUserUseCase
import org.koin.dsl.module

val authModule = module {
    single { AuthRepositoryErrorFactory }
    single<AuthManager> {
        AuthManagerImpl(get(), get(), get(), get(), get(), get(), get(), get(), get())
    }

    single<SignInUseCase> { SignInUseCaseImpl(get()) }
    single<ResetEmailUseCase> { ResetEmailUseCaseImpl(get()) }
    single<SendVerificationEmailUseCase> { SendVerificationEmailUseCaseImpl(get()) }
    single<IsEmailVerifiedUseCase> { IsEmailVerifiedUseCaseImpl(get()) }
    single<ObserveEmailValidationUseCase> { ObserveEmailValidationUseCaseImpl(get()) }
    single<ThereIsLoggedUserUseCase> { ThereIsLoggedUserUseCaseImpl(get()) }
    single<SignOutUseCase> { SignOutUseCaseImpl(get()) }
    single<CreateUserWithEmailUseCase> { CreateUserWithEmailUseCaseImpl(get()) }
    single<GetUserEmailUseCase> { GetUserEmailUseCaseImpl(get()) }
}