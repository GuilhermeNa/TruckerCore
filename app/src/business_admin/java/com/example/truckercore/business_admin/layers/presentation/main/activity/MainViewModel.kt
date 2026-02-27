package com.example.truckercore.business_admin.layers.presentation.main.activity

import com.example.truckercore.core.my_lib.classes.Email
import com.example.truckercore.core.my_lib.classes.Name
import com.example.truckercore.core.my_lib.expressions.get
import com.example.truckercore.layers.domain.use_case.authentication.GetUserEmailUseCase
import com.example.truckercore.layers.domain.use_case.authentication.GetUserNameUseCase
import com.example.truckercore.layers.presentation.base.abstractions.view_model.BaseViewModel

class MainViewModel(
    getUserNameUseCase: GetUserNameUseCase,
    getUserEmailUseCase: GetUserEmailUseCase
): BaseViewModel() {

    val name: Name = getUserNameUseCase().get()
    val email: Email = getUserEmailUseCase().get()




}