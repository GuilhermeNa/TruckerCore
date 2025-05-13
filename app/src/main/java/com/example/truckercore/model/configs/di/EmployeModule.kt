package com.example.truckercore.model.configs.di

import com.example.truckercore.model.modules.employee.admin.use_cases.GetAdminUseCase
import com.example.truckercore.model.modules.employee.admin.use_cases.GetAdminUseCaseImpl
import com.example.truckercore.model.modules.employee.autonomous.use_cases.GetAutonomousUseCase
import com.example.truckercore.model.modules.employee.autonomous.use_cases.GetAutonomousUseCaseImpl
import com.example.truckercore.model.modules.employee.driver.use_cases.GetDriverUseCase
import com.example.truckercore.model.modules.employee.driver.use_cases.GetDriverUseCaseImpl
import org.koin.dsl.module

val employeeModule = module {
    single<GetAdminUseCase> { GetAdminUseCaseImpl(get()) }
    single<GetAutonomousUseCase> { GetAutonomousUseCaseImpl(get()) }
    single<GetDriverUseCase> { GetDriverUseCaseImpl(get()) }
}