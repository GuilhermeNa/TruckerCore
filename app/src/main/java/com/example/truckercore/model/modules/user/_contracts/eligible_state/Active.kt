package com.example.truckercore.model.modules.user._contracts.eligible_state

import com.example.truckercore._utils.classes.Email
import com.example.truckercore.model.modules.user._contracts.UserEligible
import com.example.truckercore.model.modules.user.data.UserID
import com.example.truckercore.model.modules.user.exceptions.EligibleStateException

class Active : EligibleState {

    override fun <T : UserEligible<T>> register(
        newEmail: Email, newUserId: UserID, eligible: T
    ) = throw EligibleStateException(REGISTER_ERR_MSG)

    override fun <T : UserEligible<T>> suspend(eligible: T): T =
        eligible.copyWith(state = Suspend())

    override fun <T : UserEligible<T>> reactivate(eligible: T) =
        throw EligibleStateException(REACTIVATE_ERR_MSG)

    companion object {
        private const val REGISTER_ERR_MSG = "User has already been registered."
        private const val REACTIVATE_ERR_MSG = "Reactivation is not allowed for active users."
    }

}