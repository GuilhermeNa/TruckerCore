package com.example.truckercore.model.modules.employee._shared.factory

import com.example.truckercore._shared.classes.Email
import com.example.truckercore._shared.classes.FullName
import com.example.truckercore.model.infrastructure.security.data.enums.Role
import com.example.truckercore.model.modules.company.data.CompanyID
import com.example.truckercore.model.modules.user.data.UserID

data class EmployeeForm(
    val companyId: CompanyID,
    val name: FullName,
    val role: Role,
    val email: Email?,
    val userId: UserID?
)
