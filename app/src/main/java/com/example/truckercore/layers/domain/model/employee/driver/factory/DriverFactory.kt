package com.example.truckercore.layers.domain.model.employee.driver.factory

import com.example.truckercore.data.modules.employee._shared.factory.EmployeeForm
import com.example.truckercore.data.modules.employee.driver.data.Driver
import com.example.truckercore.data.modules.employee.driver.data.DriverID
import com.example.truckercore.data.modules.user._contracts.eligible_state.Active
import com.example.truckercore.data.modules.user._contracts.eligible_state.Unregistered
import com.example.truckercore.data.modules._shared.enums.PersistenceState
import com.example.truckercore.data.modules._shared.exceptions.FactoryException

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
            persistenceState = PersistenceState.ACTIVE,
            eligibleState = Unregistered(),
            userId = null
        )
    }

    fun registered(form: EmployeeForm): Driver {
        val validEmail = form.email ?: throw FactoryException(MISSING_EMAIL_MESSAGE)
        val validUserId = form.userId ?: throw FactoryException(MISSING_USER_ID_MESSAGE)

        return Driver(
            id = DriverID.generate(),
            companyId = form.companyId,
            name = form.name,
            persistenceState = PersistenceState.ACTIVE,
            eligibleState = Active(),
            email = validEmail,
            userId = validUserId
        )
    }

}