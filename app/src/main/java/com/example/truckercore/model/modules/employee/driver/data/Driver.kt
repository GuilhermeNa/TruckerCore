package com.example.truckercore.model.modules.employee.driver.data

import com.example.truckercore._utils.classes.Email
import com.example.truckercore._utils.classes.FullName
import com.example.truckercore.model.modules._shared.contracts.entity.Entity
import com.example.truckercore.model.modules._shared.enums.PersistenceState
import com.example.truckercore.model.modules.aggregation.transport_unit.contracts.Conductor
import com.example.truckercore.model.modules.aggregation.transport_unit.data.TransportUnitID
import com.example.truckercore.model.modules.company.data.CompanyID
import com.example.truckercore.model.modules.employee._shared.contracts.Employee
import com.example.truckercore.model.modules.user._contracts.UserEligible
import com.example.truckercore.model.modules.user._contracts.eligible_state.EligibleState
import com.example.truckercore.model.modules.user.data.UserID

data class Driver(
    override val id: DriverID,
    override val name: FullName,
    override val email: Email?,
    override val companyId: CompanyID,
    override val userId: UserID?,
    override val persistenceState: PersistenceState,
    override val eligibleState: EligibleState,
    override val transportUnitID: TransportUnitID? = null
) : Entity<Driver>, Employee, UserEligible<Driver>, Conductor {

    override fun copyWith(persistence: PersistenceState): Driver {
        return copy(persistenceState = persistence)
    }

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