package com.example.truckercore.shared.modules.personal_data.use_cases.implementations

import com.example.truckercore.shared.modules.personal_data.repositories.interfaces.PersonalDataRepository
import com.example.truckercore.shared.modules.personal_data.use_cases.interfaces.CheckPersonalDataExistenceUseCase
import com.example.truckercore.shared.utils.Response
import kotlinx.coroutines.flow.Flow

internal class CheckPersonalDataExistenceUseCaseImpl(
    override val repository: PersonalDataRepository
) : CheckPersonalDataExistenceUseCase {

    override suspend fun execute(id: String): Flow<Response<Boolean>> {
        return repository.entityExists(id)
    }

}