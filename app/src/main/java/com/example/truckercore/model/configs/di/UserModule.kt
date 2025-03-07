package com.example.truckercore.model.configs.di

import com.example.truckercore.model.configs.app_constants.Collection
import com.example.truckercore.model.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.model.modules.user.mapper.UserMapper
import com.example.truckercore.model.modules.user.repository.UserRepository
import com.example.truckercore.model.modules.user.repository.UserRepositoryImpl
import com.example.truckercore.model.modules.user.use_cases.implementations.CheckUserExistenceUseCaseImpl
import com.example.truckercore.model.modules.user.use_cases.implementations.CreateMasterUserUseCaseImpl
import com.example.truckercore.model.modules.user.use_cases.implementations.CreateUserUseCaseImpl
import com.example.truckercore.model.modules.user.use_cases.implementations.DeleteUserUseCaseImpl
import com.example.truckercore.model.modules.user.use_cases.implementations.GetUserUseCaseImpl
import com.example.truckercore.model.modules.user.use_cases.implementations.UpdateUserUseCaseImpl
import com.example.truckercore.model.modules.user.use_cases.interfaces.CheckUserExistenceUseCase
import com.example.truckercore.model.modules.user.use_cases.interfaces.CreateMasterUserUseCase
import com.example.truckercore.model.modules.user.use_cases.interfaces.CreateUserUseCase
import com.example.truckercore.model.modules.user.use_cases.interfaces.DeleteUserUseCase
import com.example.truckercore.model.modules.user.use_cases.interfaces.GetUserUseCase
import com.example.truckercore.model.modules.user.use_cases.interfaces.UpdateUserUseCase
import org.koin.dsl.module

val userModule = module {
    single<UserRepository> { UserRepositoryImpl(get(), Collection.USER) }
    single { UserMapper() }

    //--

    single<CreateMasterUserUseCase> {
        CreateMasterUserUseCaseImpl(get(), get(), get())
    }
    single<CheckUserExistenceUseCase> {
        CheckUserExistenceUseCaseImpl(Permission.VIEW_USER, get(), get())
    }
    single<CreateUserUseCase> {
        CreateUserUseCaseImpl(Permission.CREATE_USER, get(), get(), get(), get())
    }
    single<DeleteUserUseCase> {
        DeleteUserUseCaseImpl(Permission.DELETE_USER, get(), get(), get())
    }
    single<GetUserUseCase> {
        GetUserUseCaseImpl(Permission.VIEW_USER, get(), get(), get(), get())
    }
    single<UpdateUserUseCase> {
        UpdateUserUseCaseImpl(Permission.UPDATE_USER, get(), get(), get(), get(), get())
    }

}