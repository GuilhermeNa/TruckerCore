package com.example.truckercore.business_admin.di

import com.example.truckercore.business_admin.view_model.view_models.BaSplashFragmentViewModel
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val businessAdminModule = module {
    viewModel<BaSplashFragmentViewModel> { BaSplashFragmentViewModel(get(), get(), get()) }
    viewModel<EmailAuthViewModel> { EmailAuthViewModel(get()) }
}