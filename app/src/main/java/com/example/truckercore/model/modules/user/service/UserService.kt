package com.example.truckercore.model.modules.user.service

import com.example.truckercore.model.shared.utils.sealeds.AppResult
import com.example.truckercore.model.shared.value_classes.Email

interface UserService {

    suspend fun hasUserWithEmail(email: Email): AppResult<Boolean>

}