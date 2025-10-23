package com.example.truckercore.core.di

import com.example.truckercore.data.infrastructure.data_source.data.impl.FirestoreDataSourceImpl
import com.example.truckercore.data.infrastructure.data_source.data.impl.FirestoreDataSourceErrorMapperImpl
import com.example.truckercore.data.infrastructure.data_source.data.impl.FirestoreSpecificationInterpreterImpl
import com.example.truckercore.data.infrastructure.data_source.writer.impl.FirestoreInstructionExecutor
import com.example.truckercore.data.infrastructure.data_source.writer.impl.FirestoreInstructionInterpreter
import com.example.truckercore.data.infrastructure.data_source.auth.abstractions.AuthSource
import com.example.truckercore.data.infrastructure.data_source.auth.contracts.AuthSourceErrorMapper
import com.example.truckercore.data.infrastructure.data_source.data.abstractions.DataSource
import com.example.truckercore.data.infrastructure.data_source.data.contracts.DataSourceErrorMapper
import com.example.truckercore.data.infrastructure.data_source.data.contracts.DataSourceSpecificationInterpreter
import com.example.truckercore.data.infrastructure.data_source.writer.impl.ApiInstructionExecutor
import com.example.truckercore.data.infrastructure.data_source.writer.contracts.ApiInstructionInterpreter
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import org.koin.dsl.module

val firebaseModule = module {
    single { Firebase.auth }
    single { Firebase.firestore }
    single { Firebase.storage }

    // Data Source
    single<DataSourceErrorMapper> { FirestoreDataSourceErrorMapperImpl() }
    single<DataSourceSpecificationInterpreter> { FirestoreSpecificationInterpreterImpl(get()) }
    single<DataSource> { FirestoreDataSourceImpl(get(), get()) }

    // Auth Source
    single<AuthSourceErrorMapper> { com.example.truckercore.data.data_source.auth.impl.FirebaseAuthSourceErrorMapperImpl() }
    single<AuthSource> {
        com.example.truckercore.data.data_source.auth.impl.FirebaseAuthSourceImpl(
            get(),
            get()
        )
    }

    // Writer Source
    single<ApiInstructionInterpreter> { FirestoreInstructionInterpreter(get()) }
    single<ApiInstructionExecutor> { FirestoreInstructionExecutor(get(), get()) }

}


