package com.example.truckercore.model.modules.authentication.use_cases.interfaces

import com.example.truckercore.model.shared.utils.sealeds.AppResult

interface IsEmailVerifiedUseCase {

    operator fun invoke(): AppResult<Boolean>

}