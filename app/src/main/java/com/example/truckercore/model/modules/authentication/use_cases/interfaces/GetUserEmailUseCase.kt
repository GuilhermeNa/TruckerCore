package com.example.truckercore.model.modules.authentication.use_cases.interfaces

import com.example.truckercore._utils.classes.Email
import com.example.truckercore._utils.classes.AppResponse

interface GetUserEmailUseCase {

    fun invoke(): AppResponse<Email>

}