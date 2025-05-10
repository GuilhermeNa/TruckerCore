package com.example.truckercore.model.modules.user.factory

import com.example.truckercore.model.infrastructure.security.data.enums.Role
import com.example.truckercore.model.modules.authentication.data.UID
import com.example.truckercore.model.modules.company.data.CompanyID

data class UserForm(
    val companyId: CompanyID,
    val uid: UID,
    val role: Role
)
