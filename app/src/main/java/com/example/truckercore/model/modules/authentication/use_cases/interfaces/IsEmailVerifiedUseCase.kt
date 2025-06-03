package com.example.truckercore.model.modules.authentication.use_cases.interfaces

import com.example.truckercore._shared.classes.AppResult

interface IsEmailVerifiedUseCase {

    operator fun invoke(): AppResult<Boolean>

}