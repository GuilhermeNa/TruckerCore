package com.example.truckercore.model.modules.aggregation.system_access.use_cases.is_user_registered_in_system

import com.example.truckercore._shared.classes.AppResult
import com.example.truckercore.model.modules.authentication.data.UID

interface IsUserRegisteredInSystemUseCase {

    suspend operator fun invoke(uid: UID): AppResult<Boolean>

}