package com.example.truckercore.configs.di

import com.example.truckercore.configs.app_constants.Collection
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.modules.employee.driver.mapper.DriverMapper
import com.example.truckercore.modules.employee.driver.repository.DriverRepository
import com.example.truckercore.modules.employee.driver.repository.DriverRepositoryImpl
import com.example.truckercore.modules.employee.driver.service.DriverService
import com.example.truckercore.modules.employee.driver.service.DriverServiceImpl
import com.example.truckercore.modules.employee.driver.use_cases.implementations.AggregateDriverWithDetailsImpl
import com.example.truckercore.modules.employee.driver.use_cases.implementations.CheckDriverExistenceUseCaseImpl
import com.example.truckercore.modules.employee.driver.use_cases.implementations.CreateDriverUseCaseImpl
import com.example.truckercore.modules.employee.driver.use_cases.implementations.DeleteDriverUseCaseImpl
import com.example.truckercore.modules.employee.driver.use_cases.implementations.GetDriverUseCaseImpl
import com.example.truckercore.modules.employee.driver.use_cases.implementations.UpdateDriverUseCaseImpl
import com.example.truckercore.modules.employee.driver.use_cases.interfaces.AggregateDriverWithDetails
import com.example.truckercore.modules.employee.driver.use_cases.interfaces.CheckDriverExistenceUseCase
import com.example.truckercore.modules.employee.driver.use_cases.interfaces.CreateDriverUseCase
import com.example.truckercore.modules.employee.driver.use_cases.interfaces.DeleteDriverUseCase
import com.example.truckercore.modules.employee.driver.use_cases.interfaces.GetDriverUseCase
import com.example.truckercore.modules.employee.driver.use_cases.interfaces.UpdateDriverUseCase
import org.koin.dsl.module

val driverModule = module {
    single<DriverRepository> { DriverRepositoryImpl(get(), Collection.DRIVER) }
    single<DriverService> { DriverServiceImpl(get(), get(), get()) }
    single { DriverMapper() }

    //--

    single<AggregateDriverWithDetails> {
        AggregateDriverWithDetailsImpl(get(), get(), get())
    }
    single<CreateDriverUseCase> {
        CreateDriverUseCaseImpl(Permission.CREATE_DRIVER, get(), get(), get(), get())
    }
    single<UpdateDriverUseCase> {
        UpdateDriverUseCaseImpl(Permission.UPDATE_DRIVER, get(), get(), get(), get(), get())
    }
    single<CheckDriverExistenceUseCase> {
        CheckDriverExistenceUseCaseImpl(Permission.VIEW_DRIVER, get(), get())
    }
    single<GetDriverUseCase> {
        GetDriverUseCaseImpl(Permission.UPDATE_DRIVER, get(), get(), get(), get())
    }
    single<DeleteDriverUseCase> {
        DeleteDriverUseCaseImpl(Permission.UPDATE_DRIVER, get(), get(), get())
    }

}