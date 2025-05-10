package com.example.truckercore.model.modules.employee.admin.data

import com.example.truckercore._utils.classes.Email
import com.example.truckercore._utils.classes.FullName
import com.example.truckercore.model.configs.annotations.InternalUseOnly
import com.example.truckercore.model.modules._contracts.Entity
import com.example.truckercore.model.modules.company.data.CompanyID
import com.example.truckercore.model.modules.employee._contracts.Employee
import com.example.truckercore.model.modules.user._contracts.UserEligible
import com.example.truckercore.model.modules.user._contracts.eligible_state.EligibleState
import com.example.truckercore.model.modules.user._contracts.eligible_state.Unregistered
import com.example.truckercore.model.modules.user.data.UserID
import com.example.truckercore.model.shared.enums.Persistence

data class Admin(
    override val id: AdminID,
    override val name: FullName,
    override val companyId: CompanyID,
    override val persistence: Persistence = Persistence.ACTIVE,
    override val email: Email? = null,
    override val userId: UserID? = null,
    override val state: EligibleState = Unregistered()
) : Entity, Employee, UserEligible<Admin> {

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
            state = state
        )
    }

    override fun register(newEmail: Email, newUserId: UserID): Admin {
        return state.register(newEmail, newUserId, this)
    }

    override fun suspendRegister(): Admin {
        return state.suspend(this)
    }

    override fun reactivateRegister(): Admin {
        return state.reactivate(this)
    }

}

