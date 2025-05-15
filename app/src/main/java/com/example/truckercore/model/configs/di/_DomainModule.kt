package com.example.truckercore.model.configs.di

import serviceModule

val domainModules = listOf(
    firebaseModule,
    authModule,
    securityModule,
    repositoriesModule,
    serviceModule,
    utilModule,
    userModule,
    employeeModule,
    systemAccessModule,
    sessionModule
)