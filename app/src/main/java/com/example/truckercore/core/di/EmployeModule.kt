package com.example.truckercore.core.di

import com.example.truckercore.data.modules.employee.admin.use_cases.GetAdminUseCase
import com.example.truckercore.data.modules.employee.admin.use_cases.GetAdminUseCaseImpl
import com.example.truckercore.data.modules.employee.autonomous.use_cases.GetAutonomousUseCase
import com.example.truckercore.data.modules.employee.autonomous.use_cases.GetAutonomousUseCaseImpl
import com.example.truckercore.data.modules.employee.driver.use_cases.GetDriverUseCase
import org.koin.dsl.module

val employeeModule = module {
    single<GetAdminUseCase> { GetAdminUseCaseImpl(get()) }
    single<GetAutonomousUseCase> { GetAutonomousUseCaseImpl(get()) }
    single<GetDriverUseCase> {
        com.example.truckercore.layers.domain.model.employee.driver.use_cases.GetDriverUseCaseImpl(
            get()
        )
    }
}