package com.example.truckercore.configs.di

import com.example.truckercore.configs.app_constants.Collection
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.modules.fleet.shared.module.licensing.mapper.LicensingMapper
import com.example.truckercore.modules.fleet.shared.module.licensing.repository.LicensingRepository
import com.example.truckercore.modules.fleet.shared.module.licensing.repository.LicensingRepositoryImpl
import com.example.truckercore.modules.fleet.shared.module.licensing.service.LicensingService
import com.example.truckercore.modules.fleet.shared.module.licensing.service.LicensingServiceImpl
import com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.implementations.AggregateLicensingWithFilesUseCaseImpl
import com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.implementations.CheckLicensingExistenceUseCaseImpl
import com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.implementations.CreateLicensingUseCaseImpl
import com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.implementations.DeleteLicensingUseCaseImpl
import com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.implementations.GetLicensingUseCaseImpl
import com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.implementations.UpdateLicensingUseCaseImpl
import com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.interfaces.AggregateLicensingWithFilesUseCase
import com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.interfaces.CheckLicensingExistenceUseCase
import com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.interfaces.CreateLicensingUseCase
import com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.interfaces.DeleteLicensingUseCase
import com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.interfaces.GetLicensingUseCase
import com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.interfaces.UpdateLicensingUseCase
import org.koin.dsl.module

val licensingModule = module {
    single<LicensingRepository> { LicensingRepositoryImpl(get(), Collection.LICENSING) }
    single<LicensingService> { LicensingServiceImpl(get(), get(), get()) }
    single { LicensingMapper() }

    //--

    single<AggregateLicensingWithFilesUseCase> {
        AggregateLicensingWithFilesUseCaseImpl(get(), get())
    }
    single<CheckLicensingExistenceUseCase> {
        CheckLicensingExistenceUseCaseImpl(Permission.VIEW_LICENSING, get(), get())
    }
    single<CreateLicensingUseCase> {
        CreateLicensingUseCaseImpl(Permission.CREATE_LICENSING, get(), get(), get(), get())
    }
    single<DeleteLicensingUseCase> {
        DeleteLicensingUseCaseImpl(Permission.DELETE_LICENSING, get(), get(), get())
    }
    single<GetLicensingUseCase> {
        GetLicensingUseCaseImpl(Permission.VIEW_LICENSING, get(), get(), get(), get())
    }
    single<UpdateLicensingUseCase> {
        UpdateLicensingUseCaseImpl(Permission.UPDATE_LICENSING, get(), get(), get(), get(), get())
    }

}