package com.example.truckercore.model.configs.di

import com.example.truckercore.model.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.model.infrastructure.security.permissions.service.PermissionServiceImpl
import org.koin.dsl.module

val securityModule = module {
    single<PermissionService> { PermissionServiceImpl() }
}