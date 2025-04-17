package com.example.truckercore.model.modules.employee.admin.data

import com.example.truckercore.model.shared.enums.Persistence
import com.example.truckercore.model.shared.interfaces.data.dto.EmployeeDto

data class AdminDto(
    override val id: String? = null,
    override val name: String? = null,
    override val email: String? = null,
    override val companyId: String?= null,
    override val persistence: Persistence? = null,
): EmployeeDto {
}