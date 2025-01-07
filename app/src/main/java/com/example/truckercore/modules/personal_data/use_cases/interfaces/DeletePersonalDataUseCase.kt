package com.example.truckercore.modules.personal_data.use_cases.interfaces

import com.example.truckercore.modules.user.entity.User

internal interface DeletePersonalDataUseCase {

    fun execute(user: User, id: String)

}