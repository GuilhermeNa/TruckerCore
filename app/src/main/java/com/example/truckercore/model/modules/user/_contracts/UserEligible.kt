package com.example.truckercore.model.modules.user._contracts

import com.example.truckercore._utils.classes.Email
import com.example.truckercore.model.configs.annotations.InternalUseOnly
import com.example.truckercore.model.modules.user._contracts.eligible_state.EligibleState
import com.example.truckercore.model.modules.user.data.UserID

interface UserEligible<T : UserEligible<T>> {

    val userId: UserID?

    val eligibleState: EligibleState

    @InternalUseOnly
    fun copyWith(
        email: Email? = null,
        userId: UserID? = null,
        state: EligibleState
    ): T

    fun registerSystemUser(newEmail: Email, newUserId: UserID): T

    fun suspendSystemUser(): T

    fun reactivateSystemUser(): T

}

