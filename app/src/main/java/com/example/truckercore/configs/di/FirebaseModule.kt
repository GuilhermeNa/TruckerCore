package com.example.truckercore.configs.di

import com.example.truckercore.configs.app_constants.Collection
import com.example.truckercore.infrastructure.database.firebase.implementations.FirebaseConverterImpl
import com.example.truckercore.infrastructure.database.firebase.implementations.FirebaseQueryBuilderImpl
import com.example.truckercore.infrastructure.database.firebase.implementations.FirebaseRepositoryImpl
import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseConverter
import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseQueryBuilder
import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseRepository
import com.example.truckercore.modules.central.dto.CentralDto
import com.example.truckercore.shared.modules.personal_data.dtos.PersonalDataDto
import com.example.truckercore.shared.modules.storage_file.dtos.StorageFileDto
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import org.koin.dsl.module

val firebaseModule = module {
    single { Firebase.auth }
    single { Firebase.firestore }
    single { Firebase.storage }
    converterModule
    firebaseRepositoryModule
    single<FirebaseQueryBuilder> { FirebaseQueryBuilderImpl(get()) }
}

val firebaseRepositoryModule = module {
    single<FirebaseRepository<CentralDto>> {
        FirebaseRepositoryImpl(get(), get(), Collection.CENTRAL)
    }
    single<FirebaseRepository<PersonalDataDto>> {
        FirebaseRepositoryImpl(get(), get(), Collection.PERSONAL_DATA)
    }
    single<FirebaseRepository<StorageFileDto>> {
        FirebaseRepositoryImpl(get(), get(), Collection.FILE)
    }
}

val converterModule = module {
    single<FirebaseConverter<CentralDto>> { FirebaseConverterImpl(CentralDto::class.java) }
    single<FirebaseConverter<PersonalDataDto>> { FirebaseConverterImpl(PersonalDataDto::class.java) }
    single<FirebaseConverter<StorageFileDto>> { FirebaseConverterImpl(StorageFileDto::class.java) }
}