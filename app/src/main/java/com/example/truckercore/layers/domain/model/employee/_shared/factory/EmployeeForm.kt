package com.example.truckercore.layers.domain.model.employee._shared.factory

import com.example.truckercore.core.classes.Email
import com.example.truckercore.core.classes.FullName
import com.example.truckercore.data.infrastructure.security.data.enums.Role
import com.example.truckercore.data.modules.company.data.CompanyID
import com.example.truckercore.data.modules.user.data.UserID

data class EmployeeForm(
    val companyId: CompanyID,
    val name: FullName,
    val role: Role,
    val email: Email?,
    val userId: UserID?
)
