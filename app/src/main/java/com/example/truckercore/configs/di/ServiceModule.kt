package com.example.truckercore.configs.di

import com.example.truckercore.modules.business_central.validator.BusinessCentralValidationStrategy
import com.example.truckercore.modules.user.validator.UserValidationStrategy
import com.example.truckercore.shared.abstractions.ValidatorStrategy
import com.example.truckercore.shared.resolvers.ValidatorStrategyResolver
import com.example.truckercore.shared.services.ValidatorService
import org.koin.dsl.module

val serviceModule = module {
    single<ValidatorStrategyResolver> { ValidatorStrategyResolver() }
    single<ValidatorStrategy> { BusinessCentralValidationStrategy() }
    single<ValidatorService> { ValidatorService(get(), get()) }
}