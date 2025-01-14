package com.example.truckercore.shared.modules.personal_data.use_cases.interfaces

import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.modules.personal_data.dtos.PersonalDataDto
import com.example.truckercore.shared.sealeds.Response

internal interface GetPersonalDataByParentIdUseCase: PersonalDataUseCase  {

    suspend fun execute(user: User, parentId: String): Response<List<PersonalDataDto>>

}