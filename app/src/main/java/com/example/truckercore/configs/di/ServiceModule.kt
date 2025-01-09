package com.example.truckercore.configs.di

import com.example.truckercore.modules.business_central.use_cases.implementations.GetBusinessCentralByIdUseCaseImpl
import com.example.truckercore.modules.business_central.use_cases.interfaces.GetBusinessCentralByIdUseCase
import com.example.truckercore.shared.services.ResponseHandlerService
import org.koin.dsl.module

val serviceModule = module {

    single<ResponseHandlerService> { ResponseHandlerService(get(), get()) }

}