package com.example.truckercore.model.modules.aggregation.system_access.use_cases.interfaces

import com.example.truckercore._utils.classes.AppResult
import com.example.truckercore.model.modules.authentication.data.UID

interface IsSystemAccessCompleteUseCase {

    operator fun invoke(uid: UID): AppResult<Boolean>

}