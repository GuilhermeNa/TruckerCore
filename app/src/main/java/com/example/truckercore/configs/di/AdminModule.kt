package com.example.truckercore.configs.di

import com.example.truckercore.modules.employee.admin.mapper.AdminMapper
import com.example.truckercore.modules.employee.admin.repository.AdminRepository
import com.example.truckercore.modules.employee.admin.repository.AdminRepositoryImpl
import com.example.truckercore.modules.employee.admin.use_cases.implementations.CheckAdminExistenceUseCaseImpl
import com.example.truckercore.modules.employee.admin.use_cases.implementations.CreateAdminUseCaseImpl
import com.example.truckercore.modules.employee.admin.use_cases.implementations.DeleteAdminUseCaseImpl
import com.example.truckercore.modules.employee.admin.use_cases.implementations.GetAdminByIdUseCaseImpl
import com.example.truckercore.modules.employee.admin.use_cases.implementations.UpdateAdminUseCaseImpl
import com.example.truckercore.modules.employee.admin.use_cases.interfaces.CheckAdminExistenceUseCase
import com.example.truckercore.modules.employee.admin.use_cases.interfaces.CreateAdminUseCase
import com.example.truckercore.modules.employee.admin.use_cases.interfaces.DeleteAdminUseCase
import com.example.truckercore.modules.employee.admin.use_cases.interfaces.GetAdminByIdUseCase
import com.example.truckercore.modules.employee.admin.use_cases.interfaces.UpdateAdminUseCase
import org.koin.dsl.module

val adminModule = module {
    single<AdminRepository> { AdminRepositoryImpl(get()) }
    single<AdminMapper> { AdminMapper() }

    //--

    single<CreateAdminUseCase> { CreateAdminUseCaseImpl(get(), get(), get(), get()) }
    single<UpdateAdminUseCase> { UpdateAdminUseCaseImpl(get(), get(), get(), get(), get()) }
    single<CheckAdminExistenceUseCase> { CheckAdminExistenceUseCaseImpl(get(), get()) }
    single<GetAdminByIdUseCase> { GetAdminByIdUseCaseImpl(get(), get(), get(), get()) }
    single<DeleteAdminUseCase> { DeleteAdminUseCaseImpl(get(), get(), get()) }

}