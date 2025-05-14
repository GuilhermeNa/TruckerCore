package com.example.truckercore.model.modules.user._contracts.eligible_state

import com.example.truckercore._utils.classes.Email
import com.example.truckercore.model.errors.exceptions.domain.DomainException
import com.example.truckercore.model.modules.user._contracts.UserEligible
import com.example.truckercore.model.modules.user.data.UserID

class Unregistered : EligibleState {

    override fun <T : UserEligible<T>> register(
        newEmail: Email, newUserId: UserID, eligible: T
    ): T = eligible.copyWith(newEmail, newUserId, Active())

    override fun <T : UserEligible<T>> suspend(eligible: T) =
        throw DomainException.RuleViolated(message = "$SUSPEND_ERR_MSG $eligible")

    override fun <T : UserEligible<T>> reactivate(eligible: T) =
        throw DomainException.RuleViolated(message = "$REACTIVATE_ERR_MESSAGE $eligible")

    companion object {
        private const val SUSPEND_ERR_MSG =
            "User has not been registered in the system yet, so they cannot be suspended."

        private const val REACTIVATE_ERR_MESSAGE =
            "Only suspended users can have their access reactivated."
    }

}