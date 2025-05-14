package com.example.truckercore.model.modules.user._contracts.eligible_state

import com.example.truckercore._utils.classes.Email
import com.example.truckercore.model.errors.exceptions.domain.DomainException
import com.example.truckercore.model.modules.user._contracts.UserEligible
import com.example.truckercore.model.modules.user.data.UserID

class Suspend : EligibleState {

    override fun <T : UserEligible<T>> register(
        newEmail: Email, newUserId: UserID, eligible: T
    ) = throw DomainException.RuleViolated(message = "$REGISTER_ERR_CODE $eligible")

    override fun <T : UserEligible<T>> suspend(eligible: T) =
        throw DomainException.RuleViolated(message = "$SUSPEND_ERR_CODE $eligible")

    override fun <T : UserEligible<T>> reactivate(eligible: T): T =
        eligible.copyWith(state = Active())

    companion object {
        private const val REGISTER_ERR_CODE = "Registration is not allowed for suspended users."
        private const val SUSPEND_ERR_CODE = "User is already suspended."
    }

}