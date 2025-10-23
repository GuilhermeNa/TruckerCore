package com.example.truckercore.layers.domain.model.employee.admin.data

import com.example.truckercore.core.classes.Email
import com.example.truckercore.core.classes.FullName
import com.example.truckercore.data.modules.company.data.CompanyID
import com.example.truckercore.data.modules.user._contracts.UserEligible
import com.example.truckercore.data.modules.user._contracts.eligible_state.EligibleState
import com.example.truckercore.data.modules.user.data.UserID
import com.example.truckercore.data.modules._shared.enums.PersistenceState
import com.example.truckercore.layers.domain.base.contracts.entity.Entity

data class Admin(
    override val id: AdminID,
    override val name: FullName,
    override val companyId: CompanyID,
    override val persistence: PersistenceState,
    override val email: Email?,
    override val userId: UserID?,
    override val eligibleState: EligibleState
) : Entity, com.example.truckercore.domain.model.employee._shared.contracts.Employee, UserEligible<Admin> {

    val companyIdValue get() = companyId.value
    val userIdValue get() = userId?.value
    val emailValue get() = email?.value
    val nameValue get() = name.value
    val idValue get() = id.value

    @com.example.truckercore.core.config.annotations.InternalUseOnly
    override fun copyWith(email: Email?, userId: UserID?, state: EligibleState): Admin {
        return this.copy(
            email = email ?: this.email,
            userId = userId ?: this.userId,
            eligibleState = state
        )
    }

    @com.example.truckercore.core.config.annotations.InternalUseOnly
    override fun copyWith(persistence: PersistenceState): Admin {
        return this.copy(persistence = persistence)
    }

    override fun registerSystemUser(newEmail: Email, newUserId: UserID): Admin {
        return eligibleState.register(newEmail, newUserId, this)
    }

    override fun suspendSystemUser(): Admin {
        return eligibleState.suspend(this)
    }

    override fun reactivateSystemUser(): Admin {
        return eligibleState.reactivate(this)
    }

}

