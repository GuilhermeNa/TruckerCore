package com.example.truckercore.configs.di

import com.example.truckercore.Initial
import com.example.truckercore.MyViewModel
import com.example.truckercore.Test
import com.example.truckercore.TestImpl
import com.example.truckercore.Ultimo
import com.example.truckercore.modules.business_central.dto.BusinessCentralDto
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val testModule = module {
    viewModel<MyViewModel> { MyViewModel(get()) }


    single<Initial> { Initial(get()) }

    single<Test<BusinessCentralDto>> { TestImpl(get(named("namedUltimo"))) }

    single<Ultimo<BusinessCentralDto>>(named("namedUltimo")) { Ultimo<BusinessCentralDto>() }

}
