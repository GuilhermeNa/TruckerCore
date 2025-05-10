package com.example.truckercore.model.modules.user._contracts.eligible_state

import com.example.truckercore.model.errors.exceptions.DomainException
import com.example.truckercore.model.modules.user._contracts.UserEligible

class Suspend : EligibleState {

    override fun <T : UserEligible<T>> register(data: EligibleData, eligible: T): T {
        throw DomainException.RuleViolated(
            message = "$REGISTER_ERR_CODE $eligible"
        )
    }

    override fun <T : UserEligible<T>> suspend(data: EligibleData, eligible: T): T {
        throw DomainException.RuleViolated(
            message = "$SUSPEND_ERR_CODE $eligible"
        )
    }

    override fun <T : UserEligible<T>> reactivate(data: EligibleData, eligible: T): T {
        return eligible.setState(data, Active())
    }

    companion object {
        private const val REGISTER_ERR_CODE = "Registration is not allowed for suspended users."
        private const val SUSPEND_ERR_CODE = "User is already suspended."

    }

}