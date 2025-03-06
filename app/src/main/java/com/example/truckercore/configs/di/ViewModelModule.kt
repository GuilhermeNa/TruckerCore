package com.example.truckercore.configs.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MyViewModel(get()) }
}