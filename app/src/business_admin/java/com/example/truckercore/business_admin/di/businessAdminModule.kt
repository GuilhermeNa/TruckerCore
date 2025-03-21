package com.example.truckercore.business_admin.di

import com.example.truckercore.business_admin.view_model.login.BaSplashFragmentViewModel
import com.example.truckercore.view_model.welcome_fragment.WelcomeFragmentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val businessAdminModule = module {
    viewModel<BaSplashFragmentViewModel> { BaSplashFragmentViewModel(get(), get(), get()) }
    viewModel<WelcomeFragmentViewModel> { WelcomeFragmentViewModel() }
}