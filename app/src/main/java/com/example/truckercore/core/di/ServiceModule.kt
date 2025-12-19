package com.example.truckercore.core.di

import com.example.truckercore.core.config.flavor.FlavorService
import org.koin.dsl.module

val serviceModule = module {
    single { FlavorService(get()) }
}