package com.example.truckercore.core.di

import com.example.truckercore.data.modules.aggregation.system_access.manager.SystemAccessManager
import com.example.truckercore.data.modules.aggregation.system_access.manager.SystemAccessManagerImpl
import com.example.truckercore.data.modules.aggregation.system_access.use_cases.create_system_access.CreateSystemAccessUseCase
import com.example.truckercore.data.modules.aggregation.system_access.use_cases.create_system_access.CreateSystemAccessUseCaseImpl
import com.example.truckercore.data.modules.aggregation.system_access.use_cases.is_user_registered_in_system.IsUserRegisteredInSystemUseCaseImpl
import com.example.truckercore.data.modules.aggregation.system_access.use_cases.is_user_registered_in_system.IsUserRegisteredInSystemUseCase
import org.koin.dsl.module

val systemAccessModule = module {
    single<SystemAccessManager> { SystemAccessManagerImpl(get(), get()) }

    single<IsUserRegisteredInSystemUseCase> { IsUserRegisteredInSystemUseCaseImpl(get()) }
    single<CreateSystemAccessUseCase> { CreateSystemAccessUseCaseImpl(get()) }
}