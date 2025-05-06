package com.example.truckercore.view_model.di

import com.example.truckercore.model.configs.build.Flavor
import com.example.truckercore.model.infrastructure.integration.preferences.PreferencesRepository
import com.example.truckercore.model.infrastructure.integration.preferences.PreferencesRepositoryImpl
import com.example.truckercore.model.infrastructure.data_source.datastore.UserPreferencesDataStore
import com.example.truckercore.view_model.use_cases.CounterUseCase
import com.example.truckercore.view_model.view_models.continue_register.ContinueRegisterViewModel
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthViewModel
import com.example.truckercore.view_model.view_models.splash.SplashViewModel
import com.example.truckercore.view_model.view_models.user_name.UserNameViewModel
import com.example.truckercore.view_model.view_models.verifying_email.VerifyingEmailViewModel
import com.example.truckercore.view_model.view_models.welcome_fragment.WelcomeFragmentViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val commonViewModelModule = module {
    single { UserPreferencesDataStore(androidContext()) }
    single<PreferencesRepository> { PreferencesRepositoryImpl(get()) }

    viewModel<SplashViewModel> { SplashViewModel(get(), get(), get(), get()) }
    viewModel<EmailAuthViewModel> { EmailAuthViewModel(get(), get()) }
    viewModel<EmailAuthViewModel> { EmailAuthViewModel(get(), get()) }
    viewModel<WelcomeFragmentViewModel> { (flavor: Flavor) -> WelcomeFragmentViewModel(flavor) }
    viewModel<VerifyingEmailViewModel> { VerifyingEmailViewModel(get(), get(), get()) }
    viewModel<UserNameViewModel> { UserNameViewModel(get(), get()) }
    viewModel<ContinueRegisterViewModel> { ContinueRegisterViewModel(get())}
    factory { CounterUseCase() }

}