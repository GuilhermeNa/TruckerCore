package com.example.truckercore.configs.di

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
import org.koin.dsl.module

val centralModule = module {
    single<BusinessCentralRepository> { BusinessCentralRepositoryImpl(get()) }

    single<CheckBusinessCentralExistenceUseCase> { CheckBusinessCentralExistenceUseCaseImpl(get(), get()) }
    single<CreateBusinessCentralUseCase> { CreateBusinessCentralUseCaseImpl(get(), get()) }
    single<DeleteBusinessCentralUseCase> { DeleteBusinessCentralUseCaseImpl(get(), get()) }
    single<GetBusinessCentralByIdUseCase> { GetBusinessCentralByIdUseCaseImpl(get(), get()) }
    single<UpdateBusinessCentralUseCase> { UpdateBusinessCentralUseCaseImpl(get(), get()) }

}