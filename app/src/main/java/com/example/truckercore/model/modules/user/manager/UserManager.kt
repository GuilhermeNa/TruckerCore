package com.example.truckercore.model.modules.user.manager

import com.example.truckercore._utils.classes.AppResult
import com.example.truckercore._utils.classes.Email

interface UserManager {

    suspend fun hasUserWithEmail(email: Email): AppResult<Boolean>

}