package com.example.truckercore.layers.presentation.base

import com.example.truckercore.domain.view_models.continue_register.ContinueRegisterViewModel
import com.example.truckercore.domain.view_models.continue_register.use_case.ContinueRegisterViewUseCase
import com.example.truckercore.domain.view_models.email_auth.EmailAuthViewModel
import com.example.truckercore.domain.view_models.email_auth.use_case.AuthenticationViewUseCase
import com.example.truckercore.domain.view_models.forget_password.ForgetPasswordViewModel
import com.example.truckercore.domain.view_models.login.LoginViewModel
import com.example.truckercore.domain.view_models.login.LoginViewUseCase
import com.example.truckercore.domain.view_models.splash.SplashViewModel
import com.example.truckercore.domain.view_models.splash.use_case.SplashViewUseCase
import com.example.truckercore.domain.view_models.user_name.UserNameViewModel
import com.example.truckercore.domain.view_models.user_name.use_case.UserNameViewUseCase
import com.example.truckercore.domain.view_models.verifying_email.VerifyingEmailViewModel
import com.example.truckercore.domain.view_models.verifying_email.use_cases.SendVerificationEmailViewUseCase
import com.example.truckercore.domain.view_models.welcome_fragment.WelcomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val commonViewModelModule = module {

    single { SplashViewUseCase(get(), get(), get()) }
    single {
        com.example.truckercore.presentation.viewmodels.view_models.forget_password.ResetPasswordViewUseCase(
            get()
        )
    }
    single { LoginViewUseCase(get()) }
    single { AuthenticationViewUseCase(get()) }
    single { ContinueRegisterViewUseCase(get(), get()) }
    single { UserNameViewUseCase(get(), get(), get()) }
    single {
        com.example.truckercore.presentation.viewmodels.view_models.verifying_email.use_cases.VerifyEmailViewUseCase(
            get(),
            get()
        )
    }
    single { SendVerificationEmailViewUseCase(get()) }
    factory { CounterUseCase() }


    viewModel<SplashViewModel> { SplashViewModel(get(), get()) }
    viewModel<LoginViewModel> { LoginViewModel(get(), get()) }
    viewModel<EmailAuthViewModel> { EmailAuthViewModel(get()) }
    viewModel<WelcomeViewModel> { WelcomeViewModel(get(), get()) }
    viewModel<VerifyingEmailViewModel> { VerifyingEmailViewModel(get(), get(), get()) }
    viewModel<UserNameViewModel> { UserNameViewModel(get(), get()) }
    viewModel<ContinueRegisterViewModel> { ContinueRegisterViewModel(get(), get()) }
    viewModel<ForgetPasswordViewModel> { ForgetPasswordViewModel(get()) }



}