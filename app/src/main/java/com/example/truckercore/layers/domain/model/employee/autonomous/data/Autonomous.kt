package com.example.truckercore.layers.domain.model.employee.autonomous.data

import com.example.truckercore.core.classes.Email
import com.example.truckercore.core.classes.FullName
import com.example.truckercore.data.modules._shared._contracts.entity.Entity
import com.example.truckercore.data.modules._shared.enums.PersistenceState
import com.example.truckercore.data.modules.company.data.CompanyID
import com.example.truckercore.data.modules.user._contracts.UserEligible
import com.example.truckercore.data.modules.user._contracts.eligible_state.EligibleState
import com.example.truckercore.data.modules.user.data.UserID

data class Autonomous(
    override val id: AutID,
    override val name: FullName,
    override val companyId: CompanyID,
    override val email: Email?,
    override val userId: UserID?,
    override val persistenceState: PersistenceState,
    override val eligibleState: EligibleState
) : Entity<Autonomous>, com.example.truckercore.domain.model.employee._shared.contracts.Employee, UserEligible<Autonomous> {

    @com.example.truckercore.core.config.annotations.InternalUseOnly
    override fun copyWith(persistence: PersistenceState): Autonomous {
        return copy(persistenceState = persistence)
    }

    @com.example.truckercore.core.config.annotations.InternalUseOnly
    override fun copyWith(email: Email?, userId: UserID?, state: EligibleState): Autonomous {
        return this.copy(
            email = email ?: this.email,
            userId = userId ?: this.userId,
            eligibleState = state
        )
    }

    override fun registerSystemUser(newEmail: Email, newUserId: UserID): Autonomous {
        return eligibleState.register(newEmail, newUserId, this)
    }

    override fun suspendSystemUser(): Autonomous {
        return eligibleState.suspend(this)
    }

    override fun reactivateSystemUser(): Autonomous {
        return eligibleState.reactivate(this)
    }

}
