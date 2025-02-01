package com.example.truckercore.shared.utils.parameters

import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.abstractions.Aggregation
import com.example.truckercore.shared.utils.sealeds.SearchCriteria

data class Parameters(
    val user: User,
    val criteria: SearchCriteria,
    val liveObserver: Boolean = false,
    val aggregation: Aggregation? = null
)