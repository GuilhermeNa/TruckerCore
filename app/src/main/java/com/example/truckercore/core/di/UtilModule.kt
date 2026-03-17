package com.example.truckercore.core.di

import com.example.truckercore.layers.domain.use_case.countdown.CountdownUseCase
import org.koin.dsl.module

val utilModule = module {
    single { CountdownUseCase() }
}