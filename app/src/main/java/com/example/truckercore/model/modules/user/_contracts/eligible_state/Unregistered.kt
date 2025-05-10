package com.example.truckercore.model.modules.user._contracts.eligible_state

import com.example.truckercore.model.errors.exceptions.DomainException
import com.example.truckercore.model.modules.user._contracts.UserEligible

class Unregistered : EligibleState {

    override fun <T : UserEligible<T>> register(data: EligibleData, eligible: T): T {
        return eligible.setState(data, Active())
    }

    override fun <T : UserEligible<T>> suspend(data: EligibleData, eligible: T): T {
        throw DomainException.RuleViolated(
            message = "$SUSPEND_ERR_MSG $eligible"
        )
    }

    override fun <T : UserEligible<T>> reactivate(data: EligibleData, eligible: T): T {
        throw DomainException.RuleViolated(
            message = "$REACTIVATE_ERR_MESSAGE $eligible"
        )
    }

    companion object {
        private const val SUSPEND_ERR_MSG =
            "User has not been registered in the system yet, so they cannot be suspended."

        private const val REACTIVATE_ERR_MESSAGE =
            "Only suspended users can have their access reactivated."
    }

}