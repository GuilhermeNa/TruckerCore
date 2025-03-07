package com.example.truckercore.model.configs.di

import com.example.truckercore.model.infrastructure.util.ExceptionHandler
import com.example.truckercore.model.modules.business_central.validator.BusinessCentralValidationStrategy
import com.example.truckercore.model.shared.abstractions.ValidatorStrategy
import com.example.truckercore.model.shared.resolvers.ValidatorStrategyResolver
import com.example.truckercore.model.shared.services.ValidatorService
import org.koin.dsl.module

val serviceModule = module {
    single { ExceptionHandler() }

    single<ValidatorStrategyResolver> { ValidatorStrategyResolver() }
    single<ValidatorStrategy> { BusinessCentralValidationStrategy() }

    // Singleton instance of ValidatorService is provided, initialized with two dependencies:
    // - The ValidatorStrategyResolver: Responsible for resolving the correct strategy at runtime.
    // - The default strategy (BusinessCentralValidationStrategy): Used as the default strategy.
    // The other validation strategies do not need to be manually injected, as the Resolver
    // handles strategy selection.
    single<ValidatorService> { ValidatorService(get(), get()) }
}