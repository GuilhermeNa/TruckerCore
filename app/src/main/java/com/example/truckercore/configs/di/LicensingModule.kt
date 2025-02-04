package com.example.truckercore.configs.di

import com.example.truckercore.configs.app_constants.Collection
import com.example.truckercore.modules.fleet.shared.module.licensing.mapper.LicensingMapper
import com.example.truckercore.modules.fleet.shared.module.licensing.repository.LicensingRepository
import com.example.truckercore.modules.fleet.shared.module.licensing.repository.LicensingRepositoryImpl
import com.example.truckercore.modules.fleet.shared.module.licensing.service.LicensingService
import com.example.truckercore.modules.fleet.shared.module.licensing.service.LicensingServiceImpl
import com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.implementations.CreateLicensingUseCaseImpl
import com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.implementations.DeleteLicensingUseCaseImpl
import com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.implementations.GetLicensingByParentIdUseCaseImpl
import com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.implementations.GetLicensingByQueryUseCaseImpl
import com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.implementations.GetLicensingUseCaseImpl
import com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.interfaces.CreateLicensingUseCase
import com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.interfaces.DeleteLicensingUseCase
import com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.interfaces.GetLicensingByParentIdsUseCase
import com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.interfaces.GetLicensingByQueryUseCase
import com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.interfaces.GetLicensingUseCase
import org.koin.core.qualifier.named
import org.koin.dsl.module

val licensingModule = module {

    single<LicensingRepository> { LicensingRepositoryImpl(get(), Collection.LICENSING) }
    single<LicensingMapper> { LicensingMapper() }
    single<LicensingService> { LicensingServiceImpl(get()) }

    //--

    single<GetLicensingUseCase> { GetLicensingUseCaseImpl(get(), get(), get(), get()) }
    single<CreateLicensingUseCase> { CreateLicensingUseCaseImpl(get(), get(), get(), get()) }
    single<DeleteLicensingUseCase> { DeleteLicensingUseCaseImpl(get(), get(), get()) }
    single<GetLicensingByParentIdsUseCase> { GetLicensingByParentIdUseCaseImpl(get(), get(), get(), get()) }
    single<GetLicensingByQueryUseCase> { GetLicensingByQueryUseCaseImpl(get(), get(), get(), get()) }
}