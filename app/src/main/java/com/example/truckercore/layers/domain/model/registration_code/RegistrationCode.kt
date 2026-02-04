package com.example.truckercore.layers.domain.model.registration_code

import com.example.truckercore.layers.domain.base.contracts.entity.EmployeeID
import com.example.truckercore.layers.domain.base.contracts.entity.Entity
import com.example.truckercore.layers.domain.base.contracts.entity.ID
import com.example.truckercore.layers.domain.base.enums.Status
import com.example.truckercore.layers.domain.base.ids.CompanyID

data class RegistrationCode(
    override val id: ID,
    override val companyId: CompanyID,
    override val status: Status,
    val employeeID: EmployeeID
): Entity
