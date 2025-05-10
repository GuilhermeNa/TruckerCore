package com.example.truckercore.model.modules.employee.admin.data

import com.example.truckercore._utils.classes.Email
import com.example.truckercore._utils.classes.FullName
import com.example.truckercore.model.configs.annotations.InternalUseOnly
import com.example.truckercore.model.modules._contracts.Entity
import com.example.truckercore.model.modules.company.data.CompanyID
import com.example.truckercore.model.modules.employee._contracts.Employee
import com.example.truckercore.model.modules.user._contracts.UserEligible
import com.example.truckercore.model.modules.user._contracts.eligible_state.EligibleData
import com.example.truckercore.model.modules.user._contracts.eligible_state.EligibleState
import com.example.truckercore.model.modules.user.data.UserID
import com.example.truckercore.model.shared.enums.Persistence

data class Admin(
    override val id: AdminID,
    override val name: FullName,
    override val companyId: CompanyID,
    override val email: Email? = null,
    override val persistence: Persistence,
    override val userId: UserID? = null,
    override val state: EligibleState
) : Entity, Employee, UserEligible<Admin> {

    val companyIdValue get() = companyId.value
    val userIdValue get() = userId?.value
    val emailValue get() = email?.value
    val nameValue get() = name.value
    val idValue get() = id.value

    @InternalUseOnly
    override fun setState(data: EligibleData, newState: EligibleState): Admin {
        return this.copy(email = data.email, userId = data.userId, state = newState)
    }

    override fun registerUser(newEmail: Email, newUserId: UserID): Admin {
        val data = EligibleData(newEmail, newUserId)
        return state.register(data, this)
    }

    override fun suspendUserRegister(): Admin {
        val data = EligibleData(email, userId)
        return state.suspend(data, this)
    }

    override fun reactivateUserRegister(): Admin {
        val data = EligibleData(email, userId)
        return state.reactivate(data, this)
    }

}

