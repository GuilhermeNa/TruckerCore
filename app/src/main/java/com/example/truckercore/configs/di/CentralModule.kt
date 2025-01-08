package com.example.truckercore.configs.di

import com.example.truckercore.modules.central.repository.CentralRepository
import com.example.truckercore.modules.central.repository.CentralRepositoryImpl
import com.example.truckercore.modules.central.use_cases.implementations.CheckCentralExistenceUseCaseImpl
import com.example.truckercore.modules.central.use_cases.implementations.CreateCentralUseCaseImpl
import com.example.truckercore.modules.central.use_cases.implementations.DeleteCentralUseCaseImpl
import com.example.truckercore.modules.central.use_cases.implementations.GetCentralByIdUseCaseImpl
import com.example.truckercore.modules.central.use_cases.implementations.UpdateCentralUseCaseImpl
import com.example.truckercore.modules.central.use_cases.interfaces.CheckCentralExistenceUseCase
import com.example.truckercore.modules.central.use_cases.interfaces.CreateCentralUseCase
import com.example.truckercore.modules.central.use_cases.interfaces.DeleteCentralUseCase
import com.example.truckercore.modules.central.use_cases.interfaces.GetCentralByIdUseCase
import com.example.truckercore.modules.central.use_cases.interfaces.UpdateCentralUseCase
import org.koin.dsl.module

val centralModule = module {
    single<CentralRepository> { CentralRepositoryImpl(get())}

    single<CheckCentralExistenceUseCase> { CheckCentralExistenceUseCaseImpl(get()) }
    single<CreateCentralUseCase> { CreateCentralUseCaseImpl(get()) }
    single<DeleteCentralUseCase> { DeleteCentralUseCaseImpl(get()) }
    single<GetCentralByIdUseCase> { GetCentralByIdUseCaseImpl(get()) }
    single<UpdateCentralUseCase> { UpdateCentralUseCaseImpl(get()) }

}