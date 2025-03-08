package com.example.truckercore.model.configs.di

import com.example.truckercore.model.configs.app_constants.Collection
import com.example.truckercore.model.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.model.modules.business_central.mapper.BusinessCentralMapper
import com.example.truckercore.model.modules.business_central.repository.BusinessCentralRepository
import com.example.truckercore.model.modules.business_central.repository.BusinessCentralRepositoryImpl
import com.example.truckercore.model.modules.business_central.use_cases.implementations.CheckBusinessCentralExistenceUseCaseImpl
import com.example.truckercore.model.modules.business_central.use_cases.implementations.CreateBusinessCentralUseCaseImpl
import com.example.truckercore.model.modules.business_central.use_cases.implementations.DeleteBusinessCentralUseCaseImpl
import com.example.truckercore.model.modules.business_central.use_cases.implementations.GetBusinessCentralUseCaseImpl
import com.example.truckercore.model.modules.business_central.use_cases.implementations.UpdateBusinessCentralUseCaseImpl
import com.example.truckercore.model.modules.business_central.use_cases.interfaces.CheckBusinessCentralExistenceUseCase
import com.example.truckercore.model.modules.business_central.use_cases.interfaces.CreateBusinessCentralUseCase
import com.example.truckercore.model.modules.business_central.use_cases.interfaces.DeleteBusinessCentralUseCase
import com.example.truckercore.model.modules.business_central.use_cases.interfaces.GetBusinessCentralUseCase
import com.example.truckercore.model.modules.business_central.use_cases.interfaces.UpdateBusinessCentralUseCase
import org.koin.dsl.module

val businessCentralModule = module {
    single<BusinessCentralRepository> { BusinessCentralRepositoryImpl(get(), Collection.CENTRAL) }
    single { BusinessCentralMapper() }

    //--

    single<CreateBusinessCentralUseCase> {
        CreateBusinessCentralUseCaseImpl(
            Permission.CREATE_BUSINESS_CENTRAL,
            get(),
            get(),
            get(),
            get()
        )
    }
    single<DeleteBusinessCentralUseCase> {
        DeleteBusinessCentralUseCaseImpl(Permission.DELETE_BUSINESS_CENTRAL, get(), get(), get())
    }
    single<UpdateBusinessCentralUseCase> {
        UpdateBusinessCentralUseCaseImpl(
            Permission.UPDATE_BUSINESS_CENTRAL,
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }
    single<CheckBusinessCentralExistenceUseCase> {
        CheckBusinessCentralExistenceUseCaseImpl(Permission.VIEW_BUSINESS_CENTRAL, get(), get())
    }
    single<GetBusinessCentralUseCase> {
        GetBusinessCentralUseCaseImpl(Permission.VIEW_BUSINESS_CENTRAL, get(), get(), get(), get())
    }
}

