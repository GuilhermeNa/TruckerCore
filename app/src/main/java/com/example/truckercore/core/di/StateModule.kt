package com.example.truckercore.core.di

import com.example.truckercore.layers.domain.singletons.session.SessionManager
import org.koin.dsl.module

val stateModule = module {
    single { SessionManager(get(), get()) }
}