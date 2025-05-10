package com.example.truckercore.model.modules.user._contracts.eligible_state

import com.example.truckercore.model.errors.exceptions.DomainException
import com.example.truckercore.model.modules.user._contracts.UserEligible


class Active : EligibleState {

    override fun <T : UserEligible<T>> register(data: EligibleData, eligible: T): T {
        throw DomainException.RuleViolated(
            message = "$REGISTER_ERR_MSG $eligible"
        )
    }

    override fun <T : UserEligible<T>> suspend(data: EligibleData, eligible: T): T {
        return eligible.setState(data, Suspend())
    }

    override fun <T : UserEligible<T>> reactivate(data: EligibleData, eligible: T): T {
        throw DomainException.RuleViolated(
            message = "$REACTIVATE_ERR_MSG $eligible"
        )
    }

    companion object {
        private const val REGISTER_ERR_MSG = "User has already been registered."
        private const val REACTIVATE_ERR_MSG = "Reactivation is not allowed for active users."
    }

}