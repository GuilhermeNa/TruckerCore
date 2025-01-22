package com.example.truckercore.shared.modules.personal_data.use_cases.interfaces

import com.example.truckercore.shared.sealeds.Response
import kotlinx.coroutines.flow.Flow

internal interface CheckPersonalDataExistenceUseCase: PersonalDataUseCase {

    suspend fun execute(id: String): Flow<Response<Unit>>

}