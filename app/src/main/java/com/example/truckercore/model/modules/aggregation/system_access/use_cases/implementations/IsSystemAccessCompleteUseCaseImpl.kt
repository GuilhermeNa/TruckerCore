package com.example.truckercore.model.modules.aggregation.system_access.use_cases.implementations

import com.example.truckercore._utils.classes.AppResult
import com.example.truckercore.model.infrastructure.integration.data.for_app.repository.DataRepository
import com.example.truckercore.model.modules.aggregation.system_access.use_cases.interfaces.IsSystemAccessCompleteUseCase
import com.example.truckercore.model.modules.authentication.data.UID

class IsSystemAccessCompleteUseCaseImpl(
    private val repository: DataRepository
): IsSystemAccessCompleteUseCase {

    override fun invoke(uid: UID): AppResult<Boolean> {

    }

}