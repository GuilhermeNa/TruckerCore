package com.example.truckercore.model.modules.aggregation.system_access.manager

import com.example.truckercore._utils.classes.AppResult
import com.example.truckercore.model.modules.aggregation.system_access.factory.SystemAccessForm
import com.example.truckercore.model.modules.authentication.data.UID

interface SystemAccessManager {

    suspend fun createSystemAccess(form: SystemAccessForm): AppResult<Unit>

    suspend fun isUserRegistered(uid: UID): AppResult<Boolean>

}