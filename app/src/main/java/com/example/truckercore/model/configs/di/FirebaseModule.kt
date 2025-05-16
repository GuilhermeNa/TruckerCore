package com.example.truckercore.model.configs.di

import com.example.truckercore.model.infrastructure.data_source.firebase.auth.FirebaseAuthSource
import com.example.truckercore.model.infrastructure.data_source.firebase.auth.FirebaseAuthSourceErrorMapper
import com.example.truckercore.model.infrastructure.data_source.firebase.data.FirestoreDataSource
import com.example.truckercore.model.infrastructure.data_source.firebase.data.FirestoreDataSourceErrorMapper
import com.example.truckercore.model.infrastructure.data_source.firebase.data.FirestoreSpecificationInterpreter
import com.example.truckercore.model.infrastructure.data_source.firebase.writer.FirestoreInstructionExecutor
import com.example.truckercore.model.infrastructure.data_source.firebase.writer.FirestoreInstructionInterpreter
import com.example.truckercore.model.infrastructure.integration.auth.for_api.AuthSource
import com.example.truckercore.model.infrastructure.integration.auth.for_api.AuthSourceErrorMapper
import com.example.truckercore.model.infrastructure.integration.data.for_api.DataSource
import com.example.truckercore.model.infrastructure.integration.data.for_api.DataSourceErrorMapper
import com.example.truckercore.model.infrastructure.integration.data.for_api.DataSourceSpecificationInterpreter
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_api.ApiInstructionExecutor
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_api.contracts.ApiInstructionInterpreter
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
    single<DataSourceErrorMapper> { FirestoreDataSourceErrorMapper() }
    single<DataSourceSpecificationInterpreter> { FirestoreSpecificationInterpreter(get()) }
    single<DataSource> { FirestoreDataSource(get(), get()) }

    // Auth Source
    single<AuthSourceErrorMapper> { FirebaseAuthSourceErrorMapper() }
    single<AuthSource> { FirebaseAuthSource(get(), get()) }

    // Writer Source
    single<ApiInstructionInterpreter> { FirestoreInstructionInterpreter(get()) }
    single<ApiInstructionExecutor> { FirestoreInstructionExecutor(get(), get()) }

}


