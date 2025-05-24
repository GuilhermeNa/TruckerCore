package com.example.truckercore.model.modules.employee.driver.data

import com.example.truckercore._utils.classes.Email
import com.example.truckercore._utils.classes.FullName
import com.example.truckercore.model.modules._shared.contracts.entity.Entity
import com.example.truckercore.model.modules.company.data.CompanyID
import com.example.truckercore.model.modules.employee._shared.contracts.Employee
import com.example.truckercore.model.modules.user._contracts.UserEligible
import com.example.truckercore.model.modules.user._contracts.eligible_state.EligibleState
import com.example.truckercore.model.modules.user.data.UserID
import com.example.truckercore.model.modules._shared.enums.PersistenceState

data class Driver(
    override val id: DriverID,
    override val name: FullName,
    override val email: Email?,
    override val companyId: CompanyID,
    override val userId: UserID?,
    override val persistence: PersistenceState,
    override val eligibleState: EligibleState
) : Entity, Employee, UserEligible<Driver> {

    val companyIdValue get() = companyId.value
    val userIdValue get() = userId?.value
    val emailValue get() = email?.value
    val nameValue get() = name.value
    val idValue get() = id.value

    override fun copyWith(email: Email?, userId: UserID?, state: EligibleState): Driver {
        return this.copy(
            email = email ?: this.email,
            userId = userId ?: this.userId,
            eligibleState = state
        )
    }

    override fun registerSystemUser(newEmail: Email, newUserId: UserID): Driver {
        return eligibleState.register(newEmail, newUserId, this)
    }

    override fun suspendSystemUser(): Driver {
        return eligibleState.suspend(this)
    }

    override fun reactivateSystemUser(): Driver {
        return eligibleState.reactivate(this)
    }

}