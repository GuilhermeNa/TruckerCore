package com.example.truckercore.model.modules.user.use_cases

import com.example.truckercore.model.modules.user.data.User
import com.example.truckercore.model.modules.user.specification.UserSpec
import com.example.truckercore._shared.classes.AppResponse

interface GetUserUserCase {

    suspend operator fun invoke(spec: UserSpec): AppResponse<User>

}