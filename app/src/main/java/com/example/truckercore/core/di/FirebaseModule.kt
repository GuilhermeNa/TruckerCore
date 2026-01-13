package com.example.truckercore.core.di

import com.example.truckercore.layers.data.base.instruction._contracts.InstructionInterpreter
import com.example.truckercore.layers.data.data_source.instruction.InstructionInterpreterImpl
import com.example.truckercore.layers.data.base.specification._contracts.SpecificationInterpreter
import com.example.truckercore.layers.data.base.specification.api_impl.SpecificationInterpreterImpl
import com.example.truckercore.layers.data.data_source.auth.AuthSource
import com.example.truckercore.layers.data.data_source.auth.AuthSourceErrorMapper
import com.example.truckercore.layers.data.data_source.auth.AuthSourceImpl
import com.example.truckercore.layers.data.data_source.data.DataSource
import com.example.truckercore.layers.data.data_source.data.DataSourceErrorMapper
import com.example.truckercore.layers.data.data_source.data.DataSourceImpl
import com.example.truckercore.layers.data.base.instruction.abstraction.InstructionExecutor
import com.example.truckercore.layers.data.data_source.instruction.InstructionExecutorImpl
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import org.koin.dsl.module

val firebaseModule = module {
    // Firebase
    single { Firebase.auth }
    single { Firebase.firestore }
    single { Firebase.storage }

    // Auth Source
    single { AuthSourceErrorMapper() }
    single<AuthSource> { AuthSourceImpl(get(), get()) }

    // Data Source
    single { DataSourceErrorMapper() }
    single<SpecificationInterpreter> { SpecificationInterpreterImpl(get()) }
    single<DataSource> { DataSourceImpl(get(), get()) }

    // Instruction Source
    single<InstructionInterpreter> { InstructionInterpreterImpl(get()) }
    single<InstructionExecutor> { InstructionExecutorImpl(get(), get()) }

}


