package com.example.truckercore.model.modules.authentication.use_cases.interfaces

import com.example.truckercore.model.shared.utils.sealeds.AppResult
import com.example.truckercore.model.shared.value_classes.Email

interface GetUserEmailUseCase {

    fun invoke(): AppResult<Email>

}