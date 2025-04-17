package com.example.truckercore.model.configs.di

import com.example.truckercore.model.infrastructure.data_source.firebase._auth.FirebaseAuthDataSource
import com.example.truckercore.model.infrastructure.data_source.firebase._auth.FirebaseAuthDataSourceImpl
import com.example.truckercore.model.infrastructure.data_source.firebase.repository.FirebaseRepository
import com.example.truckercore.model.infrastructure.data_source.firebase.repository.FirebaseRepositoryImpl
import com.example.truckercore.model.infrastructure.data_source.firebase.util.FirebaseConverter
import com.example.truckercore.model.infrastructure.data_source.firebase.util.FirebaseQueryBuilder
import com.example.truckercore.model.infrastructure.security.authentication.repository.AuthenticationRepository
import com.example.truckercore.model.infrastructure.security.authentication.repository.AuthenticationRepositoryImpl
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import org.koin.dsl.module

val firebaseModule = module {
    single { Firebase.auth }
    single { Firebase.firestore }
    single { Firebase.storage }
    single<FirebaseAuthDataSource> { FirebaseAuthDataSourceImpl(get()) }
    single { FirebaseConverter() }
    single { FirebaseQueryBuilder(get()) }
    single<FirebaseRepository> { FirebaseRepositoryImpl(get(), get()) }
    single<AuthenticationRepository> { AuthenticationRepositoryImpl(get(), get()) }
}


