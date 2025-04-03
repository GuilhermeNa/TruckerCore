package com.example.truckercore.view_model.di

import com.example.truckercore.view.enums.Flavor
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthViewModel
import com.example.truckercore.view_model.view_models.verifying_email.VerifyingEmailReceivedArgs
import com.example.truckercore.view_model.view_models.verifying_email.VerifyingEmailViewModel
import com.example.truckercore.view_model.view_models.welcome_fragment.WelcomeFragmentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val commonViewModelModule = module {
    viewModel<EmailAuthViewModel> { EmailAuthViewModel(get()) }
    viewModel<WelcomeFragmentViewModel> { (flavor: Flavor) -> WelcomeFragmentViewModel(flavor) }
    viewModel<VerifyingEmailViewModel> { (args: VerifyingEmailReceivedArgs) ->
        VerifyingEmailViewModel(args = args, get())
    }

}