package com.example.truckercore.model.modules.aggregation.session.use_cases

import com.example.truckercore._utils.classes.AppResult
import com.example.truckercore.model.modules.aggregation.session.data.Session
import com.example.truckercore.model.modules.authentication.data.UID

interface GetSessionInfoUseCase {

    suspend operator fun invoke(uid: UID): AppResult<Session>

}