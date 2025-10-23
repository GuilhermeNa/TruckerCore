package com.example.truckercore.core.di

import com.example.truckercore.data.infrastructure.security.service.PermissionService
import org.koin.dsl.module

val securityModule = module {
    single<PermissionService> { com.example.truckercore.core.security.service.PermissionServiceImpl() }
}