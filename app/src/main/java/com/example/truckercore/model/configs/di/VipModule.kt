package com.example.truckercore.model.configs.di

import com.example.truckercore.model.configs.app_constants.Collection
import com.example.truckercore.model.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.model.modules.vip.mapper.VipMapper
import com.example.truckercore.model.modules.vip.repository.VipRepository
import com.example.truckercore.model.modules.vip.repository.VipRepositoryImpl
import com.example.truckercore.model.modules.vip.use_cases.implementations.GetVipUseCaseImpl
import com.example.truckercore.model.modules.vip.use_cases.interfaces.GetVipUseCase
import org.koin.dsl.module

val vipModule = module {
    single<VipRepository> { VipRepositoryImpl(get(), Collection.VIP) }
    single { VipMapper() }

    //--

    single<GetVipUseCase> {
        GetVipUseCaseImpl(requiredPermission = Permission.VIEW_VIP, get(), get(), get(), get())
    }


}