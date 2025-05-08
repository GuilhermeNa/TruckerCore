package com.example.truckercore.model.modules.aggregation.system_access.use_cases

import com.example.truckercore.model.shared.utils.sealeds.AppResult

interface CreateNewSystemAccessUseCase {

    suspend operator fun invoke(): AppResult<Unit>

}