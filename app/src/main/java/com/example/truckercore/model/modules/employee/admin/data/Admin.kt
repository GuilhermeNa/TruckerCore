package com.example.truckercore.model.modules.employee.admin.data

import com.example.truckercore.model.shared.value_classes.Email
import com.example.truckercore.model.shared.value_classes.FullName
import com.example.truckercore.model.modules.company.data_helper.CompanyID
import com.example.truckercore.model.modules.employee.admin.data_helper.AdminID
import com.example.truckercore.model.shared.enums.Persistence
import com.example.truckercore.model.shared.interfaces.data.entity.EmployeeEntity

data class Admin(
    override val id: AdminID,
    override val name: FullName,
    override val email: Email,
    override val companyId: CompanyID,
    override val persistence: Persistence,
): EmployeeEntity {
}