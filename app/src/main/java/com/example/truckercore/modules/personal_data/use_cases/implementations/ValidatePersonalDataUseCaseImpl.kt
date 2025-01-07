package com.example.truckercore.modules.personal_data.use_cases.implementations

import com.example.truckercore.modules.personal_data.dtos.PersonalDataDto
import com.example.truckercore.modules.personal_data.repositories.interfaces.PersonalDataRepository
import com.example.truckercore.modules.personal_data.use_cases.interfaces.ValidatePersonalDataUseCase

internal class ValidatePersonalDataUseCaseImpl(
    private val repository: PersonalDataRepository<PersonalDataDto>
) : ValidatePersonalDataUseCase {



}