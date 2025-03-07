package com.example.truckercore.model.configs.di

import com.example.truckercore.model.configs.app_constants.Collection
import com.example.truckercore.model.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.model.shared.modules.personal_data.mapper.PersonalDataMapper
import com.example.truckercore.model.shared.modules.personal_data.repository.PersonalDataRepository
import com.example.truckercore.model.shared.modules.personal_data.repository.PersonalDataRepositoryImpl
import com.example.truckercore.model.shared.modules.personal_data.service.PersonalDataService
import com.example.truckercore.model.shared.modules.personal_data.service.PersonalDataServiceImpl
import com.example.truckercore.model.shared.modules.personal_data.use_cases.implementations.CheckPersonalDataExistenceUseCaseImpl
import com.example.truckercore.model.shared.modules.personal_data.use_cases.implementations.CreatePersonalDataUseCaseImpl
import com.example.truckercore.model.shared.modules.personal_data.use_cases.implementations.DeletePersonalDataUseCaseImpl
import com.example.truckercore.model.shared.modules.personal_data.use_cases.implementations.GetPersonalDataUseCaseImpl
import com.example.truckercore.model.shared.modules.personal_data.use_cases.implementations.GetPersonalDataWithFilesUseCaseImpl
import com.example.truckercore.model.shared.modules.personal_data.use_cases.implementations.UpdatePersonalDataUseCaseImpl
import com.example.truckercore.model.shared.modules.personal_data.use_cases.interfaces.CheckPersonalDataExistenceUseCase
import com.example.truckercore.model.shared.modules.personal_data.use_cases.interfaces.CreatePersonalDataUseCase
import com.example.truckercore.model.shared.modules.personal_data.use_cases.interfaces.DeletePersonalDataUseCase
import com.example.truckercore.model.shared.modules.personal_data.use_cases.interfaces.GetPersonalDataUseCase
import com.example.truckercore.model.shared.modules.personal_data.use_cases.interfaces.GetPersonalDataWithFilesUseCase
import com.example.truckercore.model.shared.modules.personal_data.use_cases.interfaces.UpdatePersonalDataUseCase
import org.koin.dsl.module

val personalDataModule = module {
    single<PersonalDataRepository> { PersonalDataRepositoryImpl(get(), Collection.PERSONAL_DATA) }
    single<PersonalDataService> { PersonalDataServiceImpl(get(), get(), get()) }
    single { PersonalDataMapper() }

    //--

    single<GetPersonalDataWithFilesUseCase> {
        GetPersonalDataWithFilesUseCaseImpl(get(), get())
    }
    single<CheckPersonalDataExistenceUseCase> {
        CheckPersonalDataExistenceUseCaseImpl(
            Permission.VIEW_PERSONAL_DATA,
            get(), get()
        )
    }
    single<CreatePersonalDataUseCase> {
        CreatePersonalDataUseCaseImpl(
            Permission.CREATE_PERSONAL_DATA,
            get(), get(), get(), get()
        )
    }
    single<DeletePersonalDataUseCase> {
        DeletePersonalDataUseCaseImpl(
            Permission.DELETE_PERSONAL_DATA,
            get(), get(), get()
        )
    }
    single<GetPersonalDataUseCase> {
        GetPersonalDataUseCaseImpl(
            Permission.VIEW_PERSONAL_DATA,
            get(), get(), get(), get()
        )
    }
    single<UpdatePersonalDataUseCase> {
        UpdatePersonalDataUseCaseImpl(
            Permission.UPDATE_PERSONAL_DATA,
            get(), get(), get(), get(), get()
        )
    }

}