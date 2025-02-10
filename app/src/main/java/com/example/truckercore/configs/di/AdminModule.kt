package com.example.truckercore.configs.di

import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.modules.employee.admin.mapper.AdminMapper
import com.example.truckercore.modules.employee.admin.repository.AdminRepository
import com.example.truckercore.modules.employee.admin.repository.AdminRepositoryImpl
import com.example.truckercore.modules.employee.admin.use_cases.implementations.CheckAdminExistenceUseCaseImpl
import com.example.truckercore.modules.employee.admin.use_cases.implementations.CreateAdminUseCaseImpl
import com.example.truckercore.modules.employee.admin.use_cases.implementations.DeleteAdminUseCaseImpl
import com.example.truckercore.modules.employee.admin.use_cases.implementations.GetAdminUseCaseImpl
import com.example.truckercore.modules.employee.admin.use_cases.implementations.UpdateAdminUseCaseImpl
import com.example.truckercore.modules.employee.admin.use_cases.interfaces.CheckAdminExistenceUseCase
import com.example.truckercore.modules.employee.admin.use_cases.interfaces.CreateAdminUseCase
import com.example.truckercore.modules.employee.admin.use_cases.interfaces.DeleteAdminUseCase
import com.example.truckercore.modules.employee.admin.use_cases.interfaces.GetAdminUseCase
import com.example.truckercore.modules.employee.admin.use_cases.interfaces.UpdateAdminUseCase
import org.koin.dsl.module

val adminModule = module {
    single<AdminRepository> { AdminRepositoryImpl(get()) }
    single<AdminMapper> { AdminMapper() }

    //--

    single<CreateAdminUseCase> {
        CreateAdminUseCaseImpl(get(), get(), get(), get(), Permission.CREATE_ADMIN)
    }
    single<UpdateAdminUseCase> {
        UpdateAdminUseCaseImpl(get(), get(), get(), get(), get(), Permission.UPDATE_ADMIN)
    }
    single<CheckAdminExistenceUseCase> {
        CheckAdminExistenceUseCaseImpl(get(), get(), Permission.VIEW_ADMIN)
    }
    single<GetAdminUseCase> {
        GetAdminUseCaseImpl(get(), get(), get(), get(), Permission.VIEW_ADMIN)
    }
    single<DeleteAdminUseCase> {
        DeleteAdminUseCaseImpl(get(), get(), get(), Permission.DELETE_ADMIN)
    }

}