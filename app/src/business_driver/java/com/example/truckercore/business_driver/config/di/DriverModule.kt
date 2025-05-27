package com.example.truckercore.business_driver.config.di

import com.example.truckercore.business_driver.config.flavor.FlavorDriverStrategy
import com.example.truckercore.model.configs.flavor.contracts.FlavorStrategy
import org.koin.dsl.module

val driverModules = module {
    single<FlavorStrategy> { FlavorDriverStrategy() }
}