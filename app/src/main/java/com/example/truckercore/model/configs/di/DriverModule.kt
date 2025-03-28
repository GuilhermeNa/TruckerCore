package com.example.truckercore.model.configs.di

import com.example.truckercore.model.configs.app_constants.Collection
import com.example.truckercore.model.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.model.modules.person.employee.driver.factory.DriverFactory
import com.example.truckercore.model.modules.person.employee.driver.mapper.DriverMapper
import com.example.truckercore.model.modules.person.employee.driver.repository.DriverRepository
import com.example.truckercore.model.modules.person.employee.driver.repository.DriverRepositoryImpl
import com.example.truckercore.model.modules.person.employee.driver.service.DriverService
import com.example.truckercore.model.modules.person.employee.driver.service.DriverServiceImpl
import com.example.truckercore.model.modules.person.employee.driver.use_cases.implementations.CheckDriverExistenceUseCaseImpl
import com.example.truckercore.model.modules.person.employee.driver.use_cases.implementations.CreateDriverUseCaseImpl
import com.example.truckercore.model.modules.person.employee.driver.use_cases.implementations.DeleteDriverUseCaseImpl
import com.example.truckercore.model.modules.person.employee.driver.use_cases.implementations.GetDriverUseCaseImpl
import com.example.truckercore.model.modules.person.employee.driver.use_cases.implementations.UpdateDriverUseCaseImpl
import com.example.truckercore.model.modules.person.employee.driver.use_cases.interfaces.CheckDriverExistenceUseCase
import com.example.truckercore.model.modules.person.employee.driver.use_cases.interfaces.CreateDriverUseCase
import com.example.truckercore.model.modules.person.employee.driver.use_cases.interfaces.DeleteDriverUseCase
import com.example.truckercore.model.modules.person.employee.driver.use_cases.interfaces.GetDriverUseCase
import com.example.truckercore.model.modules.person.employee.driver.use_cases.interfaces.UpdateDriverUseCase
import com.example.truckercore.model.modules.user.factory.UserFactory
import org.koin.dsl.module

val driverModule = module {
    single<DriverRepository> { DriverRepositoryImpl(get(), Collection.DRIVER) }
    single<DriverService> { DriverServiceImpl(get(), get(), get()) }
    single { DriverMapper() }
    single { DriverFactory(get(), get()) }
    //--

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