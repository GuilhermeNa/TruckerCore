package com.example.truckercore.layers.presentation.base.abstractions._private

import android.os.Bundle
import com.example.truckercore.layers.domain.use_case.authentication.HasLoggedUserUseCase
import com.example.truckercore.layers.presentation.base.abstractions.LockedFragment
import com.example.truckercore.layers.presentation.base.contracts.SecureFragment
import org.koin.android.ext.android.inject

abstract class PrivateLockedFragment: LockedFragment(), SecureFragment {

    override val isUserLoggedUseCase: HasLoggedUserUseCase by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        doIfUserNotFound { navigateToErrorActivity(requireActivity()) }
    }

}