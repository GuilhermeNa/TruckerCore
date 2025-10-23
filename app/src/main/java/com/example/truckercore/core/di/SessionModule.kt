package com.example.truckercore.core.di

import com.example.truckercore.data.modules.aggregation.session.use_cases.GetSessionInfoUseCase
import com.example.truckercore.data.modules.aggregation.session.use_cases.GetSessionInfoUseCaseImpl
import org.koin.dsl.module

val sessionModule = module {
    single<GetSessionInfoUseCase> { GetSessionInfoUseCaseImpl(get(), get(), get(), get(), get(), get()) }
}