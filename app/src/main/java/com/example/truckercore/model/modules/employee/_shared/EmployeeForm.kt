package com.example.truckercore.model.modules.employee._shared

import com.example.truckercore._utils.classes.Email
import com.example.truckercore._utils.classes.FullName
import com.example.truckercore.model.infrastructure.security.data.enums.Role
import com.example.truckercore.model.modules.company.data.CompanyID

data class EmployeeForm(
    val companyId: CompanyID,
    val name: FullName,
    val email: Email,
    val role: Role
)
