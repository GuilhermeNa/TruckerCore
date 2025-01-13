package com.example.truckercore.configs.di

import com.example.truckercore.configs.app_constants.Collection
import com.example.truckercore.infrastructure.database.firebase.implementations.FirebaseConverterImpl
import com.example.truckercore.infrastructure.database.firebase.implementations.FirebaseQueryBuilderImpl
import com.example.truckercore.infrastructure.database.firebase.implementations.FirebaseRepositoryImpl
import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseConverter
import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseQueryBuilder
import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseRepository
import com.example.truckercore.modules.business_central.dto.BusinessCentralDto
import com.example.truckercore.shared.modules.storage_file.dtos.StorageFileDto
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import org.koin.core.qualifier.named
import org.koin.dsl.module

val firebaseModule = module {
    single { Firebase.auth }
    single { Firebase.firestore }
    single { Firebase.storage }
    single<FirebaseQueryBuilder> { FirebaseQueryBuilderImpl(get()) }
    single<FirebaseConverter<BusinessCentralDto>> {
        FirebaseConverterImpl(BusinessCentralDto::class.java)
    }
    single<FirebaseRepository<BusinessCentralDto>> {
        FirebaseRepositoryImpl<BusinessCentralDto>(
            get(),
            get(),
            Collection.CENTRAL
        )
    }
}

