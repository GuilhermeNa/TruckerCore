package com.example.truckercore.view_model.di

import com.example.truckercore.view.enums.Flavor
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthFragmentViewModel
import com.example.truckercore.view_model.view_models.phone_auth.PhoneAuthFragmentViewModel
import com.example.truckercore.view_model.view_models.welcome_fragment.WelcomeFragmentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val commonViewModelModule = module {
    viewModel<WelcomeFragmentViewModel> { (flavor: Flavor) -> WelcomeFragmentViewModel(flavor) }
    viewModel<PhoneAuthFragmentViewModel> { PhoneAuthFragmentViewModel(get()) }
    viewModel<EmailAuthFragmentViewModel> { EmailAuthFragmentViewModel() }
}