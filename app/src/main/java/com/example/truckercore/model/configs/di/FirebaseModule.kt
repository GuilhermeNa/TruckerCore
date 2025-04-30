package com.example.truckercore.model.configs.di

import com.example.truckercore.model.infrastructure.data_source.firebase.auth.FirebaseAuthErrorMapper
import com.example.truckercore.model.infrastructure.data_source.firebase.auth.FirebaseAuthSource
import com.example.truckercore.model.infrastructure.data_source.firebase.data.FirestoreDataSource
import com.example.truckercore.model.infrastructure.data_source.firebase.data.FirestoreErrorMapper
import com.example.truckercore.model.infrastructure.data_source.firebase.data.FirestoreSpecInterpreter
import com.example.truckercore.model.infrastructure.data_source.firebase.writer.FirestoreExecutor
import com.example.truckercore.model.infrastructure.data_source.firebase.writer.FirestoreInstInterpreter
import com.example.truckercore.model.infrastructure.integration.auth.for_api.AuthSource
import com.example.truckercore.model.infrastructure.integration.auth.for_api.AuthSourceErrorMapper
import com.example.truckercore.model.infrastructure.integration.data.for_api.DataSource
import com.example.truckercore.model.infrastructure.integration.data.for_api.DataSourceErrorMapper
import com.example.truckercore.model.infrastructure.integration.data.for_api.DataSourceSpecificationInterpreter
import com.example.truckercore.model.infrastructure.integration.writer.for_api.InstructionExecutor
import com.example.truckercore.model.infrastructure.integration.writer.for_api.InstructionInterpreter
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
    single<DataSourceErrorMapper> { FirestoreErrorMapper() }
    single<DataSourceSpecificationInterpreter<*, *>> { FirestoreSpecInterpreter(get()) }
    single<DataSource<*, *>> { FirestoreDataSource(get(), get()) }

    // Auth Source
    single { FirebaseAuthErrorMapper() }
    single<AuthSource> { FirebaseAuthSource(get(), get()) }

    // Writer Source
    single<InstructionInterpreter<*>> { FirestoreInstInterpreter(get()) }
    single<InstructionExecutor<*>> { FirestoreExecutor(get(), get()) }

}


