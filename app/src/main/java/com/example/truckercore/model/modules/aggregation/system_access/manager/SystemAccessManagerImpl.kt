package com.example.truckercore.model.modules.aggregation.system_access.manager

import com.example.truckercore._utils.classes.AppResult
import com.example.truckercore.model.modules.aggregation.system_access.factory.SystemAccessForm
import com.example.truckercore.model.modules.aggregation.system_access.use_cases.interfaces.CreateNewSystemAccessUseCase
import com.example.truckercore.model.modules.aggregation.system_access.use_cases.interfaces.IsSystemAccessCompleteUseCase
import com.example.truckercore.model.modules.authentication.data.UID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SystemAccessManagerImpl(
    private val systemAccessUseCase: CreateNewSystemAccessUseCase,
    private val isSystemAccessCompleteUseCase: IsSystemAccessCompleteUseCase
) : SystemAccessManager {

    override suspend fun createSystemAccess(form: SystemAccessForm): AppResult<Unit> =
        withContext(Dispatchers.IO) { systemAccessUseCase(form) }

    override suspend fun isAccessSystemComplete(uid: UID): AppResult<Boolean> =
        withContext(Dispatchers.IO) { isSystemAccessCompleteUseCase(uid) }

}