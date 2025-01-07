package com.example.truckercore.shared.modules.personal_data.use_cases.interfaces

import com.example.truckercore.shared.utils.Response

internal interface CheckPersonalDataExistenceUseCase: PersonalDataUseCase {

    suspend fun execute(id: String): Response<Boolean>

}