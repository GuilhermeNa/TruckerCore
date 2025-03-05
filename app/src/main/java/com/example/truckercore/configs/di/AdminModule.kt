package com.example.truckercore.configs.di

import com.example.truckercore.configs.app_constants.Collection
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.modules.person.employee.admin.mapper.AdminMapper
import com.example.truckercore.modules.person.employee.admin.repository.AdminRepository
import com.example.truckercore.modules.person.employee.admin.repository.AdminRepositoryImpl
import com.example.truckercore.modules.person.employee.admin.service.AdminService
import com.example.truckercore.modules.person.employee.admin.service.AdminServiceImpl
import com.example.truckercore.modules.person.employee.admin.use_cases.implementations.AggregateAdminWithDetailsImpl
import com.example.truckercore.modules.person.employee.admin.use_cases.implementations.CheckAdminExistenceUseCaseImpl
import com.example.truckercore.modules.person.employee.admin.use_cases.implementations.CreateAdminUseCaseImpl
import com.example.truckercore.modules.person.employee.admin.use_cases.implementations.DeleteAdminUseCaseImpl
import com.example.truckercore.modules.person.employee.admin.use_cases.implementations.GetAdminUseCaseImpl
import com.example.truckercore.modules.person.employee.admin.use_cases.implementations.UpdateAdminUseCaseImpl
import com.example.truckercore.modules.person.employee.admin.use_cases.interfaces.AggregateAdminWithDetails
import com.example.truckercore.modules.person.employee.admin.use_cases.interfaces.CheckAdminExistenceUseCase
import com.example.truckercore.modules.person.employee.admin.use_cases.interfaces.CreateAdminUseCase
import com.example.truckercore.modules.person.employee.admin.use_cases.interfaces.DeleteAdminUseCase
import com.example.truckercore.modules.person.employee.admin.use_cases.interfaces.GetAdminUseCase
import com.example.truckercore.modules.person.employee.admin.use_cases.interfaces.UpdateAdminUseCase
import org.koin.dsl.module

val adminModule = module {
    single<AdminRepository> { AdminRepositoryImpl(get(), Collection.ADMIN) }
    single<AdminService> { AdminServiceImpl(get(), get(), get()) }
    single { AdminMapper() }

    //--

    single<AggregateAdminWithDetails> {
        AggregateAdminWithDetailsImpl(get(), get(), get())
    }
    single<CreateAdminUseCase> {
        CreateAdminUseCaseImpl(Permission.CREATE_ADMIN, get(), get(), get(), get())
    }
    single<UpdateAdminUseCase> {
        UpdateAdminUseCaseImpl(Permission.UPDATE_ADMIN, get(), get(), get(), get(), get())
    }
    single<CheckAdminExistenceUseCase> {
        CheckAdminExistenceUseCaseImpl(Permission.VIEW_ADMIN, get(), get())
    }
    single<GetAdminUseCase> {
        GetAdminUseCaseImpl(Permission.VIEW_ADMIN, get(), get(), get(), get())
    }
    single<DeleteAdminUseCase> {
        DeleteAdminUseCaseImpl(Permission.DELETE_ADMIN, get(), get(), get())
    }

}