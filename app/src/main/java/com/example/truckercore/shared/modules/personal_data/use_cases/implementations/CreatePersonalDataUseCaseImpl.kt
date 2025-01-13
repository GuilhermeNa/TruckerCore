package com.example.truckercore.shared.modules.personal_data.use_cases.implementations

import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.modules.personal_data.dtos.PersonalDataDto
import com.example.truckercore.shared.modules.personal_data.repositories.interfaces.PersonalDataRepository
import com.example.truckercore.shared.modules.personal_data.use_cases.interfaces.CreatePersonalDataUseCase

internal class CreatePersonalDataUseCaseImpl(
    override val repository: PersonalDataRepository
): CreatePersonalDataUseCase {

    override suspend fun execute(user: User, dto: PersonalDataDto): String {
        TODO()
       // return repository.create(dto)
    }

}