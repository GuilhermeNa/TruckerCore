package com.example.truckercore.layers.presentation.base.abstractions.view._private

import android.os.Bundle
import com.example.truckercore.layers.domain.use_case.authentication.HasLoggedUserUseCase
import com.example.truckercore.layers.presentation.base.abstractions.view.BaseFragment
import com.example.truckercore.layers.presentation.base.contracts.SecureFragment
import org.koin.android.ext.android.inject

abstract class PrivateFragment : BaseFragment(), SecureFragment {

    override val isUserLoggedUseCase: HasLoggedUserUseCase by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        doIfUserNotFound { navigateToErrorActivity(requireActivity()) }
    }

}