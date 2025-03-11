package com.example.truckercore.model.configs.di

import com.example.truckercore.view.home.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val testModule = module {
    viewModel { MainViewModel(get()) }
}