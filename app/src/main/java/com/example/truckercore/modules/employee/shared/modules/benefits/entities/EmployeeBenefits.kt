package com.example.truckercore.modules.employee.shared.modules.benefits.entities

import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.interfaces.Entity
import java.time.LocalDateTime

data class EmployeeBenefits(
    override val businessCentralId: String,
    override val id: String?,
    override val lastModifierId: String,
    override val creationDate: LocalDateTime,
    override val lastUpdate: LocalDateTime,
    override val persistenceStatus: PersistenceStatus,
    val employeeId: String,
    val healthPlan: Boolean = false,
    val lifeInsurance: Boolean = false
): Entity
