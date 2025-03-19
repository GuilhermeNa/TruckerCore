package com.example.truckercore.business_admin.di

import com.example.truckercore.business_admin.view_model.login.fragments.BaSplashFragmentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val businessAdminModule = module {
    viewModel<BaSplashFragmentViewModel> { BaSplashFragmentViewModel(get()) }
}