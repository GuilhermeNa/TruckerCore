package com.example.truckercore.model.modules.person.employee.admin.dto

import com.example.truckercore.model.shared.enums.PersistenceStatus
import com.example.truckercore.model.shared.interfaces.Dto
import java.util.Date

internal data class AdminDto(
    override val businessCentralId: String? = null,
    override val id: String? = null,
    override val lastModifierId: String? = null,
    override val creationDate: Date? = null,
    override val lastUpdate: Date? = null,
    override val persistenceStatus: String? = null,
    val userId: String? = null,
    val name: String? = null,
    val email: String? = null,
    val employeeStatus: String? = null
) : Dto {

    override fun initializeId(newId: String) = this.copy(
        id = newId,
        persistenceStatus = PersistenceStatus.PERSISTED.name
    )

}