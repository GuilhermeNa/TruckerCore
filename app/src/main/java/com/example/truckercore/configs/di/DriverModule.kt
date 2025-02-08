package com.example.truckercore.configs.di

import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.modules.employee.driver.mapper.DriverMapper
import com.example.truckercore.modules.employee.driver.repository.DriverRepository
import com.example.truckercore.modules.employee.driver.repository.DriverRepositoryImpl
import com.example.truckercore.modules.employee.driver.use_cases.implementations.CheckDriverExistenceUseCaseImpl
import com.example.truckercore.modules.employee.driver.use_cases.implementations.CreateDriverUseCaseImpl
import com.example.truckercore.modules.employee.driver.use_cases.implementations.DeleteDriverUseCaseImpl
import com.example.truckercore.modules.employee.driver.use_cases.implementations.GetDriverUseCaseImpl
import com.example.truckercore.modules.employee.driver.use_cases.implementations.UpdateDriverUseCaseImpl
import com.example.truckercore.modules.employee.driver.use_cases.interfaces.CheckDriverExistenceUseCase
import com.example.truckercore.modules.employee.driver.use_cases.interfaces.CreateDriverUseCase
import com.example.truckercore.modules.employee.driver.use_cases.interfaces.DeleteDriverUseCase
import com.example.truckercore.modules.employee.driver.use_cases.interfaces.GetDriverUseCase
import com.example.truckercore.modules.employee.driver.use_cases.interfaces.UpdateDriverUseCase
import org.koin.dsl.module

val driverModule = module {
    single<DriverRepository> { DriverRepositoryImpl(get()) }
    single<DriverMapper> { DriverMapper() }

    //--

    single<CreateDriverUseCase> {
        CreateDriverUseCaseImpl(get(), get(), get(), get(), Permission.CREATE_DRIVER)
    }
    single<UpdateDriverUseCase> {
        UpdateDriverUseCaseImpl(get(), get(), get(), get(), get(), Permission.UPDATE_DRIVER)
    }
    single<CheckDriverExistenceUseCase> {
        CheckDriverExistenceUseCaseImpl(get(), get(), Permission.VIEW_DRIVER)
    }
    single<GetDriverUseCase> {
        GetDriverUseCaseImpl(get(), get(), get(), get(), Permission.UPDATE_DRIVER)
    }
    single<DeleteDriverUseCase> {
        DeleteDriverUseCaseImpl(get(), get(), get(), Permission.UPDATE_DRIVER)
    }

}