package com.example.truckercore.model.modules.aggregation.system_access.factory

import com.example.truckercore.model.modules.company.data.CompanyDto
import com.example.truckercore.model.modules.employee._contracts.EmployeeDto
import com.example.truckercore.model.modules.user.data.UserDto

/**
 * Data class representing the result of a successful system access creation process.
 *
 * This result aggregates all the necessary domain DTOs (Data Transfer Objects) that were
 * generated during the initialization of access for a new system user.
 *
 * @property companyDto The company data object, potentially updated with the new user's authorization.
 * @property userDto The user data object representing the authenticated individual.
 * @property employeeDto The employee data object representing the user's employment link to the company.
 */
data class SystemAccessResult(
    val companyDto: CompanyDto,
    val userDto: UserDto,
    val employeeDto: EmployeeDto
)