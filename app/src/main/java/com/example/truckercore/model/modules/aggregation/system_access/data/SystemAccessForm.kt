package com.example.truckercore.model.modules.aggregation.system_access.data

import com.example.truckercore._utils.classes.Email
import com.example.truckercore._utils.classes.FullName
import com.example.truckercore.model.infrastructure.security.data.enums.Role
import com.example.truckercore.model.modules.authentication.data.UID

data class SystemAccessForm(
    val uid: UID,
    val role: Role,
    val name: FullName,
    val email: Email
)
