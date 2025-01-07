package com.example.truckercore.shared.modules.personal_data.use_cases.interfaces

import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.modules.personal_data.dtos.PersonalDataDto
import com.example.truckercore.shared.utils.Response

internal interface GetPersonalDataByIdUseCase: PersonalDataUseCase  {

    suspend fun execute(user: User, id: String): Response<PersonalDataDto>

}