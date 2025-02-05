package com.example.truckercore.configs.di

import com.example.truckercore.configs.app_constants.Collection
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
    single<LicensingService> { LicensingServiceImpl(get(), get()) }
    single { LicensingMapper() }

    //--

    single<AggregateLicensingWithFilesUseCase> {
        AggregateLicensingWithFilesUseCaseImpl(get(), get())
    }
    single<CheckLicensingExistenceUseCase> {
        CheckLicensingExistenceUseCaseImpl(get(), get())
    }
    single<CreateLicensingUseCase> {
        CreateLicensingUseCaseImpl(get(), get(), get(), get())
    }
    single<DeleteLicensingUseCase> {
        DeleteLicensingUseCaseImpl(get(), get(), get())
    }
    single<GetLicensingUseCase> {
        GetLicensingUseCaseImpl(get(), get(), get(), get())
    }
    single<UpdateLicensingUseCase> {
        UpdateLicensingUseCaseImpl(get(), get(), get(), get(), get())
    }

}