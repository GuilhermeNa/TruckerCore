package com.example.truckercore.modules.person.employee.driver.entity

import com.example.truckercore.modules.person.employee.shared.abstractions.Employee
import com.example.truckercore.modules.person.employee.shared.enums.EmployeeStatus
import com.example.truckercore.shared.enums.PersistenceStatus
import java.time.LocalDateTime

/**
 * Represents a Driver entity within a Business Central. A Driver is an employee responsible for handling
 * transportation and logistics tasks within the business central, such as managing and operating vehicles,
 * trailers, or cargo.
 */
data class Driver(
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