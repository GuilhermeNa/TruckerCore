package com.example.truckercore.layers.domain.model.employee.driver.data

import com.example.truckercore.core.classes.Email
import com.example.truckercore.core.classes.FullName
import com.example.truckercore.data.modules._shared._contracts.entity.Entity
import com.example.truckercore.data.modules._shared.enums.PersistenceState
import com.example.truckercore.data.modules.aggregation.transport_unit.contracts.Conductor
import com.example.truckercore.data.modules.aggregation.transport_unit.data.TransportUnitID
import com.example.truckercore.data.modules.company.data.CompanyID
import com.example.truckercore.data.modules.user._contracts.UserEligible
import com.example.truckercore.data.modules.user._contracts.eligible_state.EligibleState
import com.example.truckercore.data.modules.user.data.UserID

data class Driver(
    override val id: DriverID,
    override val name: FullName,
    override val email: Email?,
    override val companyId: CompanyID,
    override val userId: UserID?,
    override val persistenceState: PersistenceState,
    override val eligibleState: EligibleState,
    override val transportUnitID: TransportUnitID? = null
) : Entity<Driver>, com.example.truckercore.domain.model.employee._shared.contracts.Employee, UserEligible<Driver>, Conductor {

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