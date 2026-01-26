package com.example.truckercore.business_admin.config

import com.example.truckercore.core.config.flavor.FlavorStrategy
import org.koin.dsl.module

val adminModules = module {
    single<FlavorStrategy> { FlavorAdminStrategy() }
}