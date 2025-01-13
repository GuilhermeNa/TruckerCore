package com.example.truckercore.configs.di

import com.example.truckercore.modules.business_central.dto.BusinessCentralDto
import com.example.truckercore.modules.business_central.entity.BusinessCentral
import com.example.truckercore.shared.services.ResponseHandlerService
import org.koin.dsl.module

val serviceModule = module {

    single<ResponseHandlerService<BusinessCentral, BusinessCentralDto>> {
        ResponseHandlerService(get(), get())
    }

}