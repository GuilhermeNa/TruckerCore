package com.example.truckercore.business_admin.config

import com.example.truckercore.business_admin.layers.domain.use_case.access.AuthorizeEmployeeAccessUseCase
import com.example.truckercore.business_admin.layers.domain.use_case.company.InitializeCompanyAccessUseCase
import com.example.truckercore.business_admin.layers.domain.use_case.employee.GenerateRegistrationCodeUseCase
import com.example.truckercore.business_admin.layers.presentation.check_in.view_model.CheckInViewModel
import com.example.truckercore.core.config.flavor.FlavorStrategy
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val adminModules = module {
    // Flavor Strategy
    single<FlavorStrategy> { FlavorAdminStrategy() }

    // ViewModel
    viewModel<CheckInViewModel> { CheckInViewModel(get(), get(), get(), get(), get()) }

    // UseCases
    single { AuthorizeEmployeeAccessUseCase(get()) }
    single { InitializeCompanyAccessUseCase(get()) }
    single { GenerateRegistrationCodeUseCase(get()) }

}