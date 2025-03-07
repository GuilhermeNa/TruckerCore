package com.example.truckercore.model.configs.di

import com.example.truckercore.model.configs.app_constants.Collection
import com.example.truckercore.model.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.model.shared.modules.file.mapper.FileMapper
import com.example.truckercore.model.shared.modules.file.repository.FileRepository
import com.example.truckercore.model.shared.modules.file.repository.FileRepositoryImpl
import com.example.truckercore.model.shared.modules.file.use_cases.implementations.CheckFileExistenceUseCaseImpl
import com.example.truckercore.model.shared.modules.file.use_cases.implementations.CreateFileUseCaseImpl
import com.example.truckercore.model.shared.modules.file.use_cases.implementations.DeleteStorageFileUseCaseImpl
import com.example.truckercore.model.shared.modules.file.use_cases.implementations.GetFileUseCaseImpl
import com.example.truckercore.model.shared.modules.file.use_cases.implementations.UpdateFileUseCaseImpl
import com.example.truckercore.model.shared.modules.file.use_cases.interfaces.CheckFileExistenceUseCase
import com.example.truckercore.model.shared.modules.file.use_cases.interfaces.CreateFileUseCase
import com.example.truckercore.model.shared.modules.file.use_cases.interfaces.GetFileUseCase
import com.example.truckercore.model.shared.modules.file.use_cases.interfaces.UpdateFileUseCase
import org.koin.dsl.module

val storageFileModule = module {
    single<FileRepository> { FileRepositoryImpl(get(), Collection.FILE) }
    single { FileMapper() }

    single<CheckFileExistenceUseCase> {
        CheckFileExistenceUseCaseImpl(Permission.VIEW_FILE, get(), get())
    }
    single<CreateFileUseCase> {
        CreateFileUseCaseImpl(Permission.CREATE_FILE, get(), get(), get(), get())
    }
    single<com.example.truckercore.model.shared.modules.file.use_cases.interfaces.DeleteStorageFileUseCase> {
        DeleteStorageFileUseCaseImpl(Permission.DELETE_FILE, get(), get(), get())
    }
    single<GetFileUseCase> {
        GetFileUseCaseImpl(Permission.VIEW_FILE, get(), get(), get(), get())
    }
    single<UpdateFileUseCase> {
        UpdateFileUseCaseImpl(Permission.UPDATE_FILE, get(), get(), get(), get(), get())
    }

}