package com.example.truckercore.modules.personal_data.use_cases.interfaces

import com.example.truckercore.modules.user.entity.User

internal interface CheckPersonalDataExistenceUseCase {

    suspend fun execute(user: User, id: String): Boolean

}