package com.example.truckercore.layers.domain.model.user

import com.example.truckercore.data.infrastructure.security.data.enums.Role
import com.example.truckercore.data.modules.authentication.data.UID
import com.example.truckercore.data.modules.company.data.CompanyID

data class UserForm(
    val companyId: CompanyID,
    val uid: UID,
    val role: Role
)
