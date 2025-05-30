package com.example.truckercore.business_admin.config.di

import com.example.truckercore.business_admin.config.flavor.FlavorAdminStrategy
import com.example.truckercore.model.configs.flavor.contracts.FlavorStrategy
import org.koin.dsl.module

val adminModules = module {
    single<FlavorStrategy> { FlavorAdminStrategy() }
}