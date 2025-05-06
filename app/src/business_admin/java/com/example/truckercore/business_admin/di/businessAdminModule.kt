package com.example.truckercore.business_admin.di

import com.example.truckercore.view_model.view_models.splash.SplashViewModel
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val businessAdminModule = module {
    viewModel<SplashViewModel> { SplashViewModel(get(), get(), get(), get()) }
    viewModel<EmailAuthViewModel> { EmailAuthViewModel(get(), get()) }
}