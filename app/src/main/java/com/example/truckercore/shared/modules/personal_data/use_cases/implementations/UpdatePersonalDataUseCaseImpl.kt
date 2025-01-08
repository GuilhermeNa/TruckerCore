package com.example.truckercore.shared.modules.personal_data.use_cases.implementations

import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.modules.personal_data.dtos.PersonalDataDto
import com.example.truckercore.shared.modules.personal_data.repositories.interfaces.PersonalDataRepository
import com.example.truckercore.shared.modules.personal_data.use_cases.interfaces.UpdatePersonalDataUseCase

internal class UpdatePersonalDataUseCaseImpl(
    override val repository: PersonalDataRepository
) : UpdatePersonalDataUseCase {

    override fun execute(user: User, dto: PersonalDataDto) {

        repository.update(dto)
    }


}