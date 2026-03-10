package com.example.truckercore.layers.domain.model.registration_code

import com.example.truckercore.layers.domain.base.contracts.EmployeeID
import com.example.truckercore.layers.domain.base.contracts.Entity
import com.example.truckercore.layers.domain.base.contracts.ID
import com.example.truckercore.layers.domain.base.enums.Status
import com.example.truckercore.layers.domain.base.ids.CompanyID

data class RegistrationCode(
    override val id: ID,
    override val companyId: CompanyID,
    override val status: Status,
    val employeeID: EmployeeID
): Entity
