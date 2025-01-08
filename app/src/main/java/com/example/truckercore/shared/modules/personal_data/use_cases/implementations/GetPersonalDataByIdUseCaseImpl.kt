package com.example.truckercore.shared.modules.personal_data.use_cases.implementations

import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.modules.personal_data.dtos.PersonalDataDto
import com.example.truckercore.shared.modules.personal_data.repositories.interfaces.PersonalDataRepository
import com.example.truckercore.shared.modules.personal_data.use_cases.interfaces.GetPersonalDataByIdUseCase
import com.example.truckercore.shared.utils.Response
import kotlinx.coroutines.flow.Flow

internal class GetPersonalDataByIdUseCaseImpl(
    override val repository: PersonalDataRepository
) : GetPersonalDataByIdUseCase {

    override suspend fun execute(user: User, id: String): Flow<Response<PersonalDataDto>> {

        return repository.fetchById(id)
    }


}