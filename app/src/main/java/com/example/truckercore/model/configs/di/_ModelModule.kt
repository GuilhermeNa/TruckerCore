package com.example.truckercore.model.configs.di

import serviceModule

val modelModules = listOf(
    firebaseModule,
    authModule,
    securityModule,
    repositoriesModule,
    serviceModule,
    userModule,
    employeeModule,
    systemAccessModule,
    sessionModule
)