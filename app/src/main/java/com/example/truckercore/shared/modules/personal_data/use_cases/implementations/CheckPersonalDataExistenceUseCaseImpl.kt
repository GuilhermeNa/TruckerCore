package com.example.truckercore.shared.modules.personal_data.use_cases.implementations

import com.example.truckercore.shared.modules.personal_data.repositories.interfaces.PersonalDataRepository
import com.example.truckercore.shared.modules.personal_data.use_cases.interfaces.CheckPersonalDataExistenceUseCase
import com.example.truckercore.shared.utils.Response

internal class CheckPersonalDataExistenceUseCaseImpl(
    override val repository: PersonalDataRepository
) : CheckPersonalDataExistenceUseCase {

    override suspend fun execute(id: String): Response<Boolean> {
        TODO()
    }

}