package com.example.truckercore.model.modules.aggregation.system_access.manager

import com.example.truckercore._shared.classes.AppResult
import com.example.truckercore.model.modules.aggregation.system_access.factory.SystemAccessForm
import com.example.truckercore.model.modules.aggregation.system_access.use_cases.create_system_access.CreateSystemAccessUseCase
import com.example.truckercore.model.modules.aggregation.system_access.use_cases.is_user_registered_in_system.IsUserRegisteredInSystemUseCase
import com.example.truckercore.model.modules.authentication.data.UID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SystemAccessManagerImpl(
    private val systemAccessUseCase: CreateSystemAccessUseCase,
    private val isUserRegisteredUseCase: IsUserRegisteredInSystemUseCase
) : SystemAccessManager {

    override suspend fun createSystemAccess(form: SystemAccessForm): AppResult<Unit> =
        withContext(Dispatchers.IO) { systemAccessUseCase(form) }

    override suspend fun isUserRegistered(uid: UID): AppResult<Boolean> =
        withContext(Dispatchers.IO) { isUserRegisteredUseCase(uid) }

}