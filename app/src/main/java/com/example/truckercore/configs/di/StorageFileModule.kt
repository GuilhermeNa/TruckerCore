package com.example.truckercore.configs.di

import com.example.truckercore.configs.app_constants.Collection
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.shared.modules.storage_file.mapper.StorageFileMapper
import com.example.truckercore.shared.modules.storage_file.repository.StorageFileRepository
import com.example.truckercore.shared.modules.storage_file.repository.StorageFileRepositoryImpl
import com.example.truckercore.shared.modules.storage_file.use_cases.implementations.CheckStorageFileExistenceUseCaseImpl
import com.example.truckercore.shared.modules.storage_file.use_cases.implementations.CreateStorageFileUseCaseImpl
import com.example.truckercore.shared.modules.storage_file.use_cases.implementations.DeleteStorageFileUseCaseImpl
import com.example.truckercore.shared.modules.storage_file.use_cases.implementations.GetStorageFileUseCaseImpl
import com.example.truckercore.shared.modules.storage_file.use_cases.implementations.UpdateStorageFileUseCaseImpl
import com.example.truckercore.shared.modules.storage_file.use_cases.interfaces.CheckStorageFileExistenceUseCase
import com.example.truckercore.shared.modules.storage_file.use_cases.interfaces.CreateStorageFileUseCase
import com.example.truckercore.shared.modules.storage_file.use_cases.interfaces.DeleteStorageFileUseCase
import com.example.truckercore.shared.modules.storage_file.use_cases.interfaces.GetStorageFileUseCase
import com.example.truckercore.shared.modules.storage_file.use_cases.interfaces.UpdateStorageFileUseCase
import org.koin.dsl.module

val storageFileModule = module {
    single<StorageFileRepository> { StorageFileRepositoryImpl(get(), Collection.FILE) }
    single { StorageFileMapper() }

    single<CheckStorageFileExistenceUseCase> {
        CheckStorageFileExistenceUseCaseImpl(Permission.VIEW_STORAGE_FILE, get(), get())
    }
    single<CreateStorageFileUseCase> {
        CreateStorageFileUseCaseImpl(Permission.CREATE_STORAGE_FILE, get(), get(), get(), get())
    }
    single<DeleteStorageFileUseCase> {
        DeleteStorageFileUseCaseImpl(Permission.DELETE_STORAGE_FILE, get(), get(), get())
    }
    single<GetStorageFileUseCase> {
        GetStorageFileUseCaseImpl(Permission.VIEW_STORAGE_FILE, get(), get(), get(), get())
    }
    single<UpdateStorageFileUseCase> {
        UpdateStorageFileUseCaseImpl(Permission.UPDATE_STORAGE_FILE, get(), get(), get(), get(), get())
    }

}