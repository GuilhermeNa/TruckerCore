package com.example.truckercore.model.modules.employee.admin.data

import com.example.truckercore._utils.classes.Email
import com.example.truckercore._utils.classes.FullName
import com.example.truckercore.model.configs.annotations.InternalUseOnly
import com.example.truckercore.model.modules._shared.contracts.entity.Entity
import com.example.truckercore.model.modules.company.data.CompanyID
import com.example.truckercore.model.modules.employee._shared.contracts.Employee
import com.example.truckercore.model.modules.user._contracts.UserEligible
import com.example.truckercore.model.modules.user._contracts.eligible_state.EligibleState
import com.example.truckercore.model.modules.user.data.UserID
import com.example.truckercore.model.modules._shared.enums.PersistenceState

data class Admin(
    override val id: AdminID,
    override val name: FullName,
    override val companyId: CompanyID,
    override val persistenceState: PersistenceState,
    override val email: Email?,
    override val userId: UserID?,
    override val eligibleState: EligibleState
) : Entity<Admin>, Employee, UserEligible<Admin> {

    val companyIdValue get() = companyId.value
    val userIdValue get() = userId?.value
    val emailValue get() = email?.value
    val nameValue get() = name.value
    val idValue get() = id.value

    @InternalUseOnly
    override fun copyWith(email: Email?, userId: UserID?, state: EligibleState): Admin {
        return this.copy(
            email = email ?: this.email,
            userId = userId ?: this.userId,
            eligibleState = state
        )
    }

    @InternalUseOnly
    override fun copyWith(persistence: PersistenceState): Admin {
        return this.copy(persistenceState = persistence)
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

