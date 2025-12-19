package com.example.truckercore.core.di

import com.example.truckercore.infra.security.service.PermissionService
import com.example.truckercore.infra.security.service.PermissionServiceImpl
import org.koin.dsl.module

val securityModule = module {
    single<PermissionService> { PermissionServiceImpl() }
}