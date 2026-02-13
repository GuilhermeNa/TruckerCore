package com.example.truckercore.core.di

import com.example.truckercore.layers.domain.use_case.user.CheckDomainUserRegisteredUseCase
import org.koin.dsl.module

val domainModule = module {
    single { CheckDomainUserRegisteredUseCase(get()) }
}