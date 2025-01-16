package com.example.truckercore.shared.modules.personal_data.use_cases.interfaces

import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.modules.personal_data.dtos.PersonalDataDto

internal interface UpdatePersonalDataUseCase: PersonalDataUseCase  {

    suspend fun execute(user: User, dto: PersonalDataDto)

}