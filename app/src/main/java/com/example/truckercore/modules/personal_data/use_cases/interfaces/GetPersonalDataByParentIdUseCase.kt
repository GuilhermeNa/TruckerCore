package com.example.truckercore.modules.personal_data.use_cases.interfaces

import com.example.truckercore.modules.personal_data.dtos.PersonalDataDto
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.utils.Response

internal interface GetPersonalDataByParentIdUseCase {

    suspend fun execute(user: User, parentId: String): Response<List<PersonalDataDto>>

}