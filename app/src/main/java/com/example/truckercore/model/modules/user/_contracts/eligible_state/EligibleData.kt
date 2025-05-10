package com.example.truckercore.model.modules.user._contracts.eligible_state

import com.example.truckercore._utils.classes.Email
import com.example.truckercore.model.modules.user.data.UserID

data class EligibleData(
    val email: Email? = null,
    val userId: UserID? = null,
)
