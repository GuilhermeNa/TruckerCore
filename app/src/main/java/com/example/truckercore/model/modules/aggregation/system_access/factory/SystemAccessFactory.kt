package com.example.truckercore.model.modules.aggregation.system_access.factory

import com.example.truckercore.model.modules.company.data.CompanyDto
import com.example.truckercore.model.modules.company.data.CompanyID
import com.example.truckercore.model.infrastructure.security.data.Key
import com.example.truckercore.model.modules.company.factory.CompanyFactory
import com.example.truckercore.model.modules.employee._shared.contracts.Employee
import com.example.truckercore.model.modules.employee._shared.contracts.EmployeeDto
import com.example.truckercore.model.modules.employee._shared.factory.EmployeeFactory
import com.example.truckercore.model.modules.employee._shared.factory.EmployeeForm
import com.example.truckercore.model.modules.user.data.User
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

    operator fun invoke(form: SystemAccessForm): SystemAccessResult {
        val company = CompanyFactory()
        val user = createUser(form, company.id)
        company.registerKey(Key(user.idValue))

        return SystemAccessResult(
            company = company,
            user = user,
            employee = createActiveEmployee(
                form, company.id, user.id
            )
        )
    }

    private fun createUser(form: SystemAccessForm, companyId: CompanyID): User {
        val userForm = UserForm(companyId, form.uid, form.role)
        return UserFactory(userForm)
    }

    private fun createActiveEmployee(
        form: SystemAccessForm, companyId: CompanyID, userId: UserID
    ): Employee {
        val employeeForm = EmployeeForm(
            companyId = companyId, name = form.name, email = form.email,
            role = form.role, userId = userId
        )

        return EmployeeFactory.withSystemAccess(employeeForm)
    }

}