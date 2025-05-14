package com.example.truckercore.model.modules.employee.admin.factory

import com.example.truckercore.model.errors.exceptions.domain.DomainException
import com.example.truckercore.model.modules.employee._shared.EmployeeForm
import com.example.truckercore.model.modules.employee.admin.data.Admin
import com.example.truckercore.model.modules.employee.admin.data.AdminID
import com.example.truckercore.model.modules.user._contracts.eligible_state.Active
import com.example.truckercore.model.modules.user._contracts.eligible_state.Unregistered
import com.example.truckercore.model.shared.enums.Persistence

object AdminFactory {

    private const val MISSING_EMAIL_MESSAGE =
        "To register an Admin with system access, an email must be provided."
    private const val MISSING_USER_ID_MESSAGE =
        "To register an Admin with system access, a user ID must be provided."

    operator fun invoke(form: EmployeeForm): Admin {
        return Admin(
            id = AdminID.generate(),
            companyId = form.companyId,
            name = form.name,
            email = form.email,
            persistence = Persistence.ACTIVE,
            state = Unregistered(),
            userId = null
        )
    }

    fun registered(form: EmployeeForm): Admin {
        val validEmail = form.email ?: throw DomainException.InvalidForCreation(MISSING_EMAIL_MESSAGE)
        val validUserId = form.userId ?: throw DomainException.InvalidForCreation(MISSING_USER_ID_MESSAGE)

        return Admin(
            id = AdminID.generate(),
            companyId = form.companyId,
            name = form.name,
            persistence = Persistence.ACTIVE,
            state = Active(),
            email = validEmail,
            userId = validUserId
        )
    }

}