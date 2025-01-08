package com.example.truckercore.modules.business_central.use_cases.interfaces

import com.example.truckercore.modules.user.entity.User

internal interface DeleteBusinessCentralUseCase {

    fun execute(user: User, id: String)

}