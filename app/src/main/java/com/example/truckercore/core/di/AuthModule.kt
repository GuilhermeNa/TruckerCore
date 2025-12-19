package com.example.truckercore.core.di

import com.example.truckercore.layers.domain.use_case.authentication.CheckUserRegistrationUseCase
import com.example.truckercore.layers.domain.use_case.authentication.CheckUserRegistrationUseCaseImpl
import com.example.truckercore.layers.domain.use_case.authentication.CreateUserProfileUseCase
import com.example.truckercore.layers.domain.use_case.authentication.CreateUserProfileUseCaseImpl
import com.example.truckercore.layers.domain.use_case.authentication.ResetPasswordUseCase
import com.example.truckercore.layers.domain.use_case.authentication.ResetPasswordUseCaseImpl
import com.example.truckercore.layers.domain.use_case.authentication.CreateUserWithEmailUseCase
import com.example.truckercore.layers.domain.use_case.authentication.CreateUserWithEmailUseCaseImpl
import com.example.truckercore.layers.domain.use_case.authentication.GetUidUseCase
import com.example.truckercore.layers.domain.use_case.authentication.GetUidUseCaseImpl
import com.example.truckercore.layers.domain.use_case.authentication.GetUserEmailUseCase
import com.example.truckercore.layers.domain.use_case.authentication.GetUserEmailUseCaseImpl
import com.example.truckercore.layers.domain.use_case.authentication.HasLoggedUserUseCase
import com.example.truckercore.layers.domain.use_case.authentication.HasLoggedUserUseCaseImpl
import com.example.truckercore.layers.domain.use_case.authentication.IsEmailVerifiedUseCase
import com.example.truckercore.layers.domain.use_case.authentication.IsEmailVerifiedUseCaseImpl
import com.example.truckercore.layers.domain.use_case.authentication.IsProfileCompleteUseCase
import com.example.truckercore.layers.domain.use_case.authentication.IsProfileCompleteUseCaseImpl
import com.example.truckercore.layers.domain.use_case.authentication.ObserveEmailValidationUseCaseImpl
import com.example.truckercore.layers.domain.use_case.authentication.SendEmailVerificationUseCase
import com.example.truckercore.layers.domain.use_case.authentication.SendEmailVerificationUseCaseImpl
import com.example.truckercore.layers.domain.use_case.authentication.SignInUseCase
import com.example.truckercore.layers.domain.use_case.authentication.SignInUseCaseImpl
import com.example.truckercore.layers.domain.use_case.authentication.SignOutUseCase
import com.example.truckercore.layers.domain.use_case.authentication.SignOutUseCaseImpl
import com.example.truckercore.layers.domain.use_case.authentication.VerifyEmailValidationUseCase
import org.koin.dsl.module

val authModule = module {
    single<SignInUseCase> { SignInUseCaseImpl(get()) }
    single<ResetPasswordUseCase> { ResetPasswordUseCaseImpl(get()) }
    single<IsEmailVerifiedUseCase> { IsEmailVerifiedUseCaseImpl(get()) }
    single<HasLoggedUserUseCase> { HasLoggedUserUseCaseImpl(get()) }
    single<GetUserEmailUseCase> { GetUserEmailUseCaseImpl(get()) }
    single<GetUidUseCase> { GetUidUseCaseImpl(get()) }
    single<SendEmailVerificationUseCase> { SendEmailVerificationUseCaseImpl(get()) }
    single<SignOutUseCase> { SignOutUseCaseImpl(get()) }
    single<VerifyEmailValidationUseCase> { ObserveEmailValidationUseCaseImpl(get()) }
    single<CheckUserRegistrationUseCase> { CheckUserRegistrationUseCaseImpl(get(), get(), get()) }
    single<CreateUserProfileUseCase> { CreateUserProfileUseCaseImpl(get()) }
    single<CreateUserWithEmailUseCase> { CreateUserWithEmailUseCaseImpl(get()) }
    single<IsProfileCompleteUseCase> { IsProfileCompleteUseCaseImpl(get()) }
}