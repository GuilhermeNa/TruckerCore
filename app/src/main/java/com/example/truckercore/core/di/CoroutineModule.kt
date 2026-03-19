package com.example.truckercore.core.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.dsl.module

val coroutineModule = module {
    single { CoroutineScope(SupervisorJob() + Dispatchers.IO) }
}