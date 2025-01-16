package com.example.truckercore.shared.modules.personal_data.use_cases.interfaces

import com.example.truckercore.modules.user.entity.User

internal interface DeletePersonalDataUseCase {

    suspend fun execute(user: User, id: String)

}