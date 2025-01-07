package com.example.truckercore.modules.personal_data.use_cases.implementations

import com.example.truckercore.modules.personal_data.dtos.PersonalDataDto
import com.example.truckercore.modules.personal_data.repositories.interfaces.PersonalDataRepository
import com.example.truckercore.modules.personal_data.use_cases.interfaces.GetPersonalDataByIdUseCase
import com.example.truckercore.modules.personal_data.use_cases.interfaces.UpdatePersonalDataStatusUseCase

internal class GetPersonalDataByIdUseCaseImpl(
    private val repository: PersonalDataRepository<PersonalDataDto>
) : GetPersonalDataByIdUseCase {



}