package com.example.truckercore.model.modules.user._contracts.eligible_state

import com.example.truckercore._utils.classes.Email
import com.example.truckercore.model.modules.user._contracts.UserEligible
import com.example.truckercore.model.modules.user.data.UserID
import com.example.truckercore.model.modules.user.exceptions.EligibleStateException

class Suspend : EligibleState {

    override fun <T : UserEligible<T>> register(
        newEmail: Email, newUserId: UserID, eligible: T
    ) = throw EligibleStateException(REGISTER_ERR_CODE)

    override fun <T : UserEligible<T>> suspend(eligible: T) =
        throw EligibleStateException(SUSPEND_ERR_CODE)

    override fun <T : UserEligible<T>> reactivate(eligible: T): T =
        eligible.copyWith(state = Active())

    companion object {
        private const val REGISTER_ERR_CODE = "Registration is not allowed for suspended users."
        private const val SUSPEND_ERR_CODE = "User is already suspended."
    }

}