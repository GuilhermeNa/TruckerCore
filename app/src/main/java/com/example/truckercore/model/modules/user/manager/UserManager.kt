package com.example.truckercore.model.modules.user.manager

import com.example.truckercore._shared.classes.AppResult
import com.example.truckercore._shared.classes.Email

interface UserManager {

    suspend fun hasUserWithEmail(email: Email): AppResult<Boolean>

}