package com.example.truckercore.model.modules.employee.autonomous.factory

import com.example.truckercore.model.modules._shared._contracts.entity.ID
import com.example.truckercore.model.modules._shared.enums.PersistenceState
import com.example.truckercore.model.modules._shared.exceptions.FactoryException
import com.example.truckercore.model.modules.employee._shared.factory.EmployeeForm
import com.example.truckercore.model.modules.employee.autonomous.data.AutID
import com.example.truckercore.model.modules.employee.autonomous.data.Autonomous
import com.example.truckercore.model.modules.user._contracts.eligible_state.Active
import com.example.truckercore.model.modules.user._contracts.eligible_state.Unregistered

object AutonomousFactory {

    private const val MISSING_EMAIL_MESSAGE =
        "To register an Autonomous with system access, an email must be provided."
    private const val MISSING_USER_ID_MESSAGE =
        "To register an Autonomous with system access, a user ID must be provided."

    operator fun invoke(form: EmployeeForm): Autonomous {
        return Autonomous(
            id = AutID(ID.generate()),
            companyId = form.companyId,
            name = form.name,
            persistenceState = PersistenceState.ACTIVE,
            eligibleState = Unregistered(),
            email = form.email,
            userId = null
        )
    }

    fun registered(form: EmployeeForm): Autonomous {
        val validEmail = form.email ?: throw FactoryException(MISSING_EMAIL_MESSAGE)
        val validUserId = form.userId ?: throw FactoryException(MISSING_USER_ID_MESSAGE)

        return Autonomous(
            id = AutID.generate(),
            companyId = form.companyId,
            name = form.name,
            persistenceState = PersistenceState.ACTIVE,
            eligibleState = Active(),
            email = validEmail,
            userId = validUserId
        )
    }

}