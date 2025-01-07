package com.example.truckercore.modules.personal_data.use_cases.interfaces

import com.example.truckercore.modules.personal_data.dtos.PersonalDataDto
import com.example.truckercore.modules.user.entity.User

internal interface CreatePersonalDataUseCase {

    fun execute(user: User, dto: PersonalDataDto): String

}