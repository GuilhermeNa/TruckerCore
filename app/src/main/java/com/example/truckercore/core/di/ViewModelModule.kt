package com.example.truckercore.core.di

import com.example.truckercore.layers.presentation.login.view_model.continue_register.ContinueRegisterViewModel
import com.example.truckercore.layers.presentation.login.view_model.email_auth.EmailAuthViewModel
import com.example.truckercore.layers.presentation.login.view_model.forget_password.ForgetPasswordViewModel
import com.example.truckercore.layers.presentation.login.view_model.login.LoginViewModel
import com.example.truckercore.layers.presentation.login.view_model.splash.SplashViewModel
import com.example.truckercore.layers.presentation.login.view_model.verifying_email.VerifyingEmailViewModel
import com.example.truckercore.layers.presentation.login.view_model.welcome_fragment.WelcomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel<ContinueRegisterViewModel> { ContinueRegisterViewModel(get(), get()) }
    viewModel<EmailAuthViewModel> { EmailAuthViewModel(get()) }
    viewModel<ForgetPasswordViewModel> { ForgetPasswordViewModel(get()) }
    viewModel<LoginViewModel> { LoginViewModel(get(), get()) }

    //TODO(REVER FRAGMENT)
    viewModel<SplashViewModel> { SplashViewModel(get(), get(), get()) }

    viewModel<VerifyingEmailViewModel> {
        VerifyingEmailViewModel(get(), get(), get(), get(), get())
    }
    viewModel<VerifyingEmailViewModel> {
        VerifyingEmailViewModel(get(), get(), get(), get(), get())
    }
    viewModel<WelcomeViewModel> { WelcomeViewModel(get(), get()) }

}