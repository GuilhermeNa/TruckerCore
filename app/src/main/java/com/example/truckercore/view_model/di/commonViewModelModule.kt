package com.example.truckercore.view_model.di

import com.example.truckercore.view_model._shared.use_cases.CounterUseCase
import com.example.truckercore.view_model.view_models.continue_register.ContinueRegisterViewModel
import com.example.truckercore.view_model.view_models.continue_register.use_case.ContinueRegisterViewUseCase
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthViewModel
import com.example.truckercore.view_model.view_models.email_auth.use_case.AuthenticationViewUseCase
import com.example.truckercore.view_model.view_models.forget_password.ForgetPasswordViewModel
import com.example.truckercore.view_model.view_models.forget_password.ResetPasswordViewUseCase
import com.example.truckercore.view_model.view_models.login.LoginViewModel
import com.example.truckercore.view_model.view_models.login.LoginViewUseCase
import com.example.truckercore.view_model.view_models.splash.SplashViewModel
import com.example.truckercore.view_model.view_models.user_name.UserNameViewModel
import com.example.truckercore.view_model.view_models.verifying_email.VerifyingEmailViewModel
import com.example.truckercore.view_model.view_models.welcome_fragment.WelcomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val commonViewModelModule = module {

    single { ResetPasswordViewUseCase(get()) }
    single { LoginViewUseCase(get()) }
    single { AuthenticationViewUseCase(get()) }
    single { ContinueRegisterViewUseCase(get(), get()) }
    factory { CounterUseCase() }

    viewModel<SplashViewModel> { SplashViewModel(get(), get(), get()) }
    viewModel<LoginViewModel> { LoginViewModel(get(), get()) }
    viewModel<EmailAuthViewModel> { EmailAuthViewModel(get()) }
    viewModel<WelcomeViewModel> { WelcomeViewModel(get()) }
    viewModel<VerifyingEmailViewModel> { VerifyingEmailViewModel(get(), get()) }
    viewModel<UserNameViewModel> { UserNameViewModel(get(), get(), get(), get()) }
    viewModel<ContinueRegisterViewModel> { ContinueRegisterViewModel(get(), get()) }
    viewModel<ForgetPasswordViewModel> { ForgetPasswordViewModel(get()) }



}