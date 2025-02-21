package com.example.truckercore.configs.di

import com.example.truckercore.infrastructure.database.firebase.repository.FirebaseAuthRepository
import com.example.truckercore.infrastructure.database.firebase.repository.FirebaseAuthRepositoryImpl
import com.example.truckercore.infrastructure.database.firebase.repository.FirebaseRepository
import com.example.truckercore.infrastructure.database.firebase.repository.FirebaseRepositoryImpl
import com.example.truckercore.infrastructure.database.firebase.util.FirebaseConverter
import com.example.truckercore.infrastructure.database.firebase.util.FirebaseQueryBuilder
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import org.koin.dsl.module

val firebaseModule = module {
    single { Firebase.auth }
    single { Firebase.firestore }
    single { Firebase.storage }
    single { FirebaseConverter() }
    single { FirebaseQueryBuilder(get()) }
    single<FirebaseRepository> { FirebaseRepositoryImpl(get(), get()) }
    single<FirebaseAuthRepository> { FirebaseAuthRepositoryImpl(get()) }
}


