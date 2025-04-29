package com.example.truckercore.view_model.di

import com.example.truckercore.view.enums.Flavor
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthViewModel
import com.example.truckercore.view_model.view_models.user_name.UserNameViewModel
import com.example.truckercore.view_model.view_models.verifying_email.VerifyingEmailViewModel
import com.example.truckercore.view_model.view_models.welcome_fragment.WelcomeFragmentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val commonViewModelModule = module {
    viewModel<EmailAuthViewModel> { (args: EmailAuthVmArgs) ->
        EmailAuthViewModel(args = args, get())
    }
    viewModel<WelcomeFragmentViewModel> { (flavor: Flavor) -> WelcomeFragmentViewModel(flavor) }
    viewModel<VerifyingEmailViewModel> { VerifyingEmailViewModel(get()) }
    viewModel<UserNameViewModel> { UserNameViewModel() }

}