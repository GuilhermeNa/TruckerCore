package com.example.truckercore.model.modules.aggregation.system_access.factory

import com.example.truckercore.model.modules._exceptions.FactoryException
import com.example.truckercore.model.modules.company.data.CompanyDto
import com.example.truckercore.model.modules.company.data.CompanyID
import com.example.truckercore.model.modules.company.factory.CompanyFactory
import com.example.truckercore.model.modules.employee._contracts.EmployeeDto
import com.example.truckercore.model.modules.employee._shared.EmployeeFactory
import com.example.truckercore.model.modules.employee._shared.EmployeeForm
import com.example.truckercore.model.modules.user.data.UserDto
import com.example.truckercore.model.modules.user.data.UserID
import com.example.truckercore.model.modules.user.factory.UserFactory
import com.example.truckercore.model.modules.user.factory.UserForm

/**
 * Factory responsible for constructing all necessary domain objects required to initialize system access.
 *
 * This factory creates and returns a [SystemAccessResult] which includes:
 * - A [CompanyDto], updated with authorized user keys.
 * - A [UserDto] created from the provided form input.
 * - An [EmployeeDto] linked to the company and user.
 *
 * The factory also validates the presence of required IDs (company and user) after creation
 * and wraps any exception into a [FactoryException] with a standardized message.
 *
 * Usage:
 * ```
 * val result: SystemAccessResult = SystemAccessFactory(form)
 * ```
 *
 * @throws FactoryException if any component creation fails, or required IDs are missing.
 */
object SystemAccessFactory {

    private const val COMPANY_ID_ERROR = "Expected a valid Company id."
    private const val USER_ID_ERROR = "Expected a valid User id."

    operator fun invoke(form: SystemAccessForm): SystemAccessResult {
        return try {
            // Company
            val companyDto = CompanyFactory()
            val companyId = CompanyID(companyDto.id ?: throw NullPointerException(COMPANY_ID_ERROR))

            // Employee
            val employeeForm = getEmployeeForm(form, companyId)
            val employeeDto = EmployeeFactory(employeeForm)

            // User
            val userForm = getUserForm(form, companyId)
            val userDto = UserFactory(userForm)
            val userId = UserID(userDto.id ?: throw NullPointerException(USER_ID_ERROR))

            // Copy company and insert new user key
            val companyDtoWithKey = companyDto.copy(authorizedKeys = setOf(userId.value))

            // return
            SystemAccessResult(
                companyDto = companyDtoWithKey,
                userDto = userDto,
                employeeDto = employeeDto
            )

        } catch (e: Exception) {
            val message = FactoryException.getMessage(this::class.java)
            throw FactoryException(message, e)
        }
    }

    /**
     * Builds an [EmployeeForm] using form input and the generated company ID.
     */
    private fun getEmployeeForm(form: SystemAccessForm, companyId: CompanyID): EmployeeForm {
        return EmployeeForm(
            companyId = companyId,
            name = form.name,
            email = form.email,
            role = form.role
        )
    }

    /**
     * Builds a [UserForm] using form input and the generated company ID.
     */
    private fun getUserForm(form: SystemAccessForm, companyId: CompanyID): UserForm {
        return UserForm(
            companyId = companyId,
            uid = form.uid,
            role = form.role
        )
    }

}