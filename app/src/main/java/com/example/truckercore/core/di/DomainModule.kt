package com.example.truckercore.core.di

import com.example.truckercore.layers.domain.use_case.company.CompleteCompanyRegistration
import com.example.truckercore.layers.domain.use_case.company.CompleteCompanyRegistrationImpl
import com.example.truckercore.layers.domain.use_case.user.CheckDomainUserRegisteredUseCase
import org.koin.dsl.module

val domainModule = module {
    single { CheckDomainUserRegisteredUseCase(get()) }
    single { CheckDomainUserRegisteredUseCase(get()) }
    single<CompleteCompanyRegistration> { CompleteCompanyRegistrationImpl(get()) }
}