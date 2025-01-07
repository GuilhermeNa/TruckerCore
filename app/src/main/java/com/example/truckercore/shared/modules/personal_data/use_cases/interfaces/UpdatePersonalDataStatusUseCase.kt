package com.example.truckercore.shared.modules.personal_data.use_cases.interfaces

import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.enums.PersistenceStatus

internal interface UpdatePersonalDataStatusUseCase: PersonalDataUseCase  {

    fun execute(user: User, id: String, newState: PersistenceStatus)

}