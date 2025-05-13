package com.example.truckercore.model.modules.user.service

import com.example.truckercore._utils.classes.AppResult
import com.example.truckercore._utils.classes.Email

interface UserService {

    suspend fun hasUserWithEmail(email: Email): AppResult<Boolean>

}