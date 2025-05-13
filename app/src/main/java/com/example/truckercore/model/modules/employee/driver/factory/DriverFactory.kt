package com.example.truckercore.model.modules.employee.driver.factory

import com.example.truckercore.model.errors.exceptions.DomainException
import com.example.truckercore.model.modules.employee._shared.EmployeeForm
import com.example.truckercore.model.modules.employee.driver.data.Driver
import com.example.truckercore.model.modules.employee.driver.data.DriverID
import com.example.truckercore.model.modules.user._contracts.eligible_state.Active
import com.example.truckercore.model.modules.user._contracts.eligible_state.Unregistered
import com.example.truckercore.model.shared.enums.Persistence

object DriverFactory {

    private const val MISSING_EMAIL_MESSAGE =
        "To register a Driver with system access, an email must be provided."
    private const val MISSING_USER_ID_MESSAGE =
        "To register a Driver with system access, a user ID must be provided."

    operator fun invoke(form: EmployeeForm): Driver {
        return Driver(
            id = DriverID.generate(),
            companyId = form.companyId,
            name = form.name,
            email = form.email,
            persistence = Persistence.ACTIVE,
            state = Unregistered(),
            userId = null
        )
    }

    fun registered(form: EmployeeForm): Driver {
        val validEmail = form.email ?: throw DomainException.InvalidForCreation(MISSING_EMAIL_MESSAGE)
        val validUserId = form.userId ?: throw DomainException.InvalidForCreation(MISSING_USER_ID_MESSAGE)

        return Driver(
            id = DriverID.generate(),
            companyId = form.companyId,
            name = form.name,
            persistence = Persistence.ACTIVE,
            state = Active(),
            email = validEmail,
            userId = validUserId
        )
    }

}