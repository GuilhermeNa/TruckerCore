package com.example.truckercore.configs.di

import com.example.truckercore.modules.person.shared.person_details.GetPersonWithDetailsUseCase
import com.example.truckercore.modules.person.shared.person_details.GetPersonWithDetailsUseCaseImpl
import org.koin.dsl.module

val personModule = module {
    single<GetPersonWithDetailsUseCase> {
        GetPersonWithDetailsUseCaseImpl(get(), get(), get(), get())
    }
}