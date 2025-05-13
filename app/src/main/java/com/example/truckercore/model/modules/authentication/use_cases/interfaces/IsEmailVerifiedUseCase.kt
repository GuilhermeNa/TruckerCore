package com.example.truckercore.model.modules.authentication.use_cases.interfaces

import com.example.truckercore._utils.classes.AppResult

interface IsEmailVerifiedUseCase {

    operator fun invoke(): AppResult<Boolean>

}