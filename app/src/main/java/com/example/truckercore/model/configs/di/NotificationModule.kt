package com.example.truckercore.model.configs.di

import com.example.truckercore.model.configs.app_constants.Collection
import com.example.truckercore.model.modules.notification.mapper.NotificationMapper
import com.example.truckercore.model.modules.notification.repository.NotificationRepository
import com.example.truckercore.model.modules.notification.repository.NotificationRepositoryImpl
import org.koin.dsl.module

val notificationModule = module {
    single<NotificationRepository> { NotificationRepositoryImpl(get(), Collection.NOTIFICATION) }
    single { NotificationMapper() }
}