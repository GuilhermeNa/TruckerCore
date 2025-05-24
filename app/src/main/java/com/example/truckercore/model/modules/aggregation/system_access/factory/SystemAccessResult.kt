package com.example.truckercore.model.modules.aggregation.system_access.factory

import com.example.truckercore.model.modules.company.data.Company
import com.example.truckercore.model.modules.employee._shared.contracts.Employee
import com.example.truckercore.model.modules.user.data.User

/**
 * Data class representing the result of a successful system access creation process.
 *
 * This result aggregates all the necessary domain DTOs (Data Transfer Objects) that were
 * generated during the initialization of access for a new system user.
 *
 * @property company The company data object, potentially updated with the new user's authorization.
 * @property user The user data object representing the authenticated individual.
 * @property employee The employee data object representing the user's employment link to the company.
 */
data class SystemAccessResult(
    val company: Company,
    val user: User,
    val employee: Employee
)