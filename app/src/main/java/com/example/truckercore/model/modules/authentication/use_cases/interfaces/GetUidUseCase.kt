package com.example.truckercore.model.modules.authentication.use_cases.interfaces

import com.example.truckercore._utils.classes.AppResult
import com.example.truckercore.model.modules.authentication.data.UID

interface GetUidUseCase {

    operator fun invoke(): AppResult<UID>

}