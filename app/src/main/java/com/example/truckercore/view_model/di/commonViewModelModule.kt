package com.example.truckercore.view_model.di

import com.example.truckercore.model.infrastructure.data_source.datastore.UserPreferencesDataStore
import com.example.truckercore.model.infrastructure.integration.preferences.PreferencesRepository
import com.example.truckercore.model.infrastructure.integration.preferences.PreferencesRepositoryImpl
import com.example.truckercore.view_model.use_cases.CounterUseCase
import com.example.truckercore.view_model.view_models.continue_register.ContinueRegisterViewModel
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthViewModel
import com.example.truckercore.view_model.view_models.forget_password.ForgetPasswordViewModel
import com.example.truckercore.view_model.view_models.forget_password.ResetPasswordViewUseCase
import com.example.truckercore.view_model.view_models.login.LoginViewModel
import com.example.truckercore.view_model.view_models.login.LoginViewUseCase
import com.example.truckercore.view_model.view_models.splash.SplashViewModel
import com.example.truckercore.view_model.view_models.user_name.UserNameViewModel
import com.example.truckercore.view_model.view_models.verifying_email.VerifyingEmailViewModel
import com.example.truckercore.view_model.view_models.welcome_fragment.WelcomeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val commonViewModelModule = module {
    single { UserPreferencesDataStore(androidContext()) }
    single<PreferencesRepository> { PreferencesRepositoryImpl(get()) }

    single { ResetPasswordViewUseCase(get()) }
    single { LoginViewUseCase(get()) }

    viewModel<SplashViewModel> { SplashViewModel(get(), get(), get()) }
    viewModel<LoginViewModel> { LoginViewModel(get(), get()) }
    viewModel<EmailAuthViewModel> { EmailAuthViewModel(get(), get()) }
    viewModel<EmailAuthViewModel> { EmailAuthViewModel(get(), get()) }
    viewModel<WelcomeViewModel> { WelcomeViewModel(get()) }
    viewModel<VerifyingEmailViewModel> { VerifyingEmailViewModel(get(), get()) }
    viewModel<UserNameViewModel> { UserNameViewModel(get(), get(), get(), get()) }
    viewModel<ContinueRegisterViewModel> { ContinueRegisterViewModel(get()) }
    viewModel<ForgetPasswordViewModel> { ForgetPasswordViewModel(get()) }

    factory { CounterUseCase() }

}