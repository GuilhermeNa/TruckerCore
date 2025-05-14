package com.example.truckercore.model.modules.authentication.use_cases.interfaces

import com.example.truckercore._utils.classes.AppResult
import com.example.truckercore._utils.classes.Email

interface ResetEmailUseCase {

    suspend operator fun invoke(email: Email): AppResult<Unit>

}