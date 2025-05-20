package com.example.truckercore.model.modules.aggregation.session.data

import com.example.truckercore.model.infrastructure.security.contracts.SystemManager
import com.example.truckercore.model.modules.authentication.contracts.Authenticable
import com.example.truckercore.model.modules.authentication.data.UID
import com.example.truckercore.model.modules.user._contracts.UserEligible

data class Session(
    val uid: UID,
    val user: Authenticable,
    val company: SystemManager,
    val person: UserEligible<*>
)