package com.example.truckercore.configs.di

import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.modules.business_central.mapper.BusinessCentralMapper
import com.example.truckercore.modules.business_central.repository.BusinessCentralRepository
import com.example.truckercore.modules.business_central.repository.BusinessCentralRepositoryImpl
import com.example.truckercore.modules.business_central.use_cases.implementations.CheckBusinessCentralExistenceUseCaseImpl
import com.example.truckercore.modules.business_central.use_cases.implementations.CreateBusinessCentralUseCaseImpl
import com.example.truckercore.modules.business_central.use_cases.implementations.DeleteBusinessCentralUseCaseImpl
import com.example.truckercore.modules.business_central.use_cases.implementations.GetBusinessCentralByIdUseCaseImpl
import com.example.truckercore.modules.business_central.use_cases.implementations.UpdateBusinessCentralUseCaseImpl
import com.example.truckercore.modules.business_central.use_cases.interfaces.CheckBusinessCentralExistenceUseCase
import com.example.truckercore.modules.business_central.use_cases.interfaces.CreateBusinessCentralUseCase
import com.example.truckercore.modules.business_central.use_cases.interfaces.DeleteBusinessCentralUseCase
import com.example.truckercore.modules.business_central.use_cases.interfaces.GetBusinessCentralByIdUseCase
import com.example.truckercore.modules.business_central.use_cases.interfaces.UpdateBusinessCentralUseCase
import org.koin.core.qualifier.named
import org.koin.dsl.module

val businessCentralModule = module {
    single<BusinessCentralRepository> { BusinessCentralRepositoryImpl(get(named("Repository_BusinessCentral"))) }
    single<BusinessCentralMapper> { BusinessCentralMapper() }

    //--

    single<CreateBusinessCentralUseCase> {
        CreateBusinessCentralUseCaseImpl(get(), get(), get())
    }
    single<DeleteBusinessCentralUseCase> {
        DeleteBusinessCentralUseCaseImpl(get(), get(), get(),Permission.DELETE_BUSINESS_CENTRAL)
    }
    single<UpdateBusinessCentralUseCase> {
        UpdateBusinessCentralUseCaseImpl(get(), get(), get(), get(), get(), Permission.UPDATE_BUSINESS_CENTRAL)
    }
    single<CheckBusinessCentralExistenceUseCase> {
        CheckBusinessCentralExistenceUseCaseImpl(get(), get(), Permission.VIEW_BUSINESS_CENTRAL)
    }
    single<GetBusinessCentralByIdUseCase> {
        GetBusinessCentralByIdUseCaseImpl(get(), get(), get(), get())
    }
}

