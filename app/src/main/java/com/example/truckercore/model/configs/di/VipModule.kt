package com.example.truckercore.model.configs.di

import com.example.truckercore.model.configs.app_constants.Collection
import com.example.truckercore.model.modules.vip.mapper.VipMapper
import com.example.truckercore.model.modules.vip.repository.VipRepository
import com.example.truckercore.model.modules.vip.repository.VipRepositoryImpl
import org.koin.dsl.module

val vipModule = module {
    single<VipRepository> { VipRepositoryImpl(get(), Collection.VIP) }
    single { VipMapper() }
}