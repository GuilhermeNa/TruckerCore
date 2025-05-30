package com.example.truckercore.model.configs.di

import com.example.truckercore.model.modules.aggregation.system_access.manager.SystemAccessManager
import com.example.truckercore.model.modules.aggregation.system_access.manager.SystemAccessManagerImpl
import com.example.truckercore.model.modules.aggregation.system_access.use_cases.interfaces.CreateNewSystemAccessUseCase
import com.example.truckercore.model.modules.aggregation.system_access.use_cases.implementations.CreateSystemAccessUseCaseImpl
import com.example.truckercore.model.modules.aggregation.system_access.use_cases.implementations.IsUserRegisteredInSystemUseCaseImpl
import com.example.truckercore.model.modules.aggregation.system_access.use_cases.interfaces.IsUserRegisteredInSystemUseCase
import org.koin.dsl.module

val systemAccessModule = module {
    single<SystemAccessManager> { SystemAccessManagerImpl(get(), get()) }

    single<IsUserRegisteredInSystemUseCase> { IsUserRegisteredInSystemUseCaseImpl(get()) }
    single<CreateNewSystemAccessUseCase> { CreateSystemAccessUseCaseImpl(get()) }
}