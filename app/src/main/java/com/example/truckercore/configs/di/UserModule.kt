package com.example.truckercore.configs.di

import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.modules.user.mapper.UserMapper
import com.example.truckercore.modules.user.repository.UserRepository
import com.example.truckercore.modules.user.repository.UserRepositoryImpl
import com.example.truckercore.modules.user.use_cases.implementations.CheckUserExistenceUseCaseImpl
import com.example.truckercore.modules.user.use_cases.implementations.CreateMasterUserUseCaseImpl
import com.example.truckercore.modules.user.use_cases.implementations.CreateUserUseCaseImpl
import com.example.truckercore.modules.user.use_cases.implementations.DeleteUserUseCaseImpl
import com.example.truckercore.modules.user.use_cases.implementations.GetUserByIdUseCaseImpl
import com.example.truckercore.modules.user.use_cases.implementations.UpdateUserUseCaseImpl
import com.example.truckercore.modules.user.use_cases.interfaces.CheckUserExistenceUseCase
import com.example.truckercore.modules.user.use_cases.interfaces.CreateMasterUserUseCase
import com.example.truckercore.modules.user.use_cases.interfaces.CreateUserUseCase
import com.example.truckercore.modules.user.use_cases.interfaces.DeleteUserUseCase
import com.example.truckercore.modules.user.use_cases.interfaces.GetUserByIdUseCase
import com.example.truckercore.modules.user.use_cases.interfaces.UpdateUserUseCase
import org.koin.dsl.module

val userModule = module {
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<UserMapper> { UserMapper() }
    single<CheckUserExistenceUseCase> {
        CheckUserExistenceUseCaseImpl(get(), get(), Permission.VIEW_USER)
    }
    single<CreateMasterUserUseCase> { CreateMasterUserUseCaseImpl(get(), get(), get()) }
    single<CreateUserUseCase> { CreateUserUseCaseImpl(get(), get(), get(), get()) }
    single<DeleteUserUseCase> {
        DeleteUserUseCaseImpl(get(), get(), get(), Permission.DELETE_USER)
    }
    single<GetUserByIdUseCase> {
        GetUserByIdUseCaseImpl(get(), get(), get(), get(), Permission.VIEW_USER)
    }
    single<UpdateUserUseCase> {
        UpdateUserUseCaseImpl(get(), get(), get(), get(), get(), Permission.UPDATE_USER)
    }
}