package com.example.truckercore.modules.employee.shared.modules.compensation.entities

import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.interfaces.Entity
import java.math.BigDecimal
import java.time.LocalDateTime

data class Compensation(
    override val masterUid: String,
    override val id: String?,
    override val lastModifierId: String,
    override val creationDate: LocalDateTime,
    override val lastUpdate: LocalDateTime,
    override val persistenceStatus: PersistenceStatus,
    val employeeId: String,
    val salary: BigDecimal? = null,
    val commissionRate: BigDecimal? = null,
    val bonus: BigDecimal? = null
): Entity {



}
