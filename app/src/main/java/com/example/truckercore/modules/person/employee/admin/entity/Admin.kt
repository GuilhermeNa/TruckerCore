package com.example.truckercore.modules.person.employee.admin.entity

import com.example.truckercore.modules.person.employee.shared.abstractions.Employee
import com.example.truckercore.modules.person.employee.shared.enums.EmployeeStatus
import com.example.truckercore.shared.enums.PersistenceStatus
import java.time.LocalDateTime

/**
 * Represents an Admin entity within a Business Central. An Admin is an employee who holds administrative
 * privileges within the business central, having access to sensitive operations and the ability to manage
 * other users within the business unit.
 *
 * This class stores essential information about the admin, such as their name, email, employee status,
 * as well as audit-related fields like creation and modification dates, and their associated business central.
 */
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
    override val employeeStatus: EmployeeStatus
) : Employee(employeeStatus = employeeStatus)
