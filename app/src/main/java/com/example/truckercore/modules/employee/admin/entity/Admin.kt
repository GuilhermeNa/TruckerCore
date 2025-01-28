package com.example.truckercore.modules.employee.admin.entity

import com.example.truckercore.modules.employee.shared.abstractions.Employee
import com.example.truckercore.modules.employee.shared.enums.EmployeeStatus
import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.modules.personal_data.entity.PersonalData
import com.example.truckercore.shared.modules.storage_file.entity.StorageFile
import java.time.LocalDateTime

data class Admin(
    override val businessCentralId: String,
    override val id: String?,
    override val lastModifierId: String,
    override val creationDate: LocalDateTime,
    override val lastUpdate: LocalDateTime,
    override val persistenceStatus: PersistenceStatus,
    override val userId: String?,
    override val name: String,
    override val email: String,
    override val photo: StorageFile? = null,
    override val personalData: List<PersonalData>? = null,
    override val employeeStatus: EmployeeStatus
) : Employee(employeeStatus = employeeStatus) {



}
