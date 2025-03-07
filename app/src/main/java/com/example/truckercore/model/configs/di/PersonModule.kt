package com.example.truckercore.model.configs.di

import com.example.truckercore.model.modules.person.shared.person_details.GetPersonWithDetailsUseCase
import com.example.truckercore.model.modules.person.shared.person_details.GetPersonWithDetailsUseCaseImpl
import org.koin.dsl.module

val personModule = module {
    single<GetPersonWithDetailsUseCase> {
        GetPersonWithDetailsUseCaseImpl(get(), get(), get(), get())
    }
}