package com.example.truckercore.model.modules.user._contracts.eligible_state

import com.example.truckercore._utils.classes.Email
import com.example.truckercore.model.modules.user._contracts.UserEligible
import com.example.truckercore.model.modules.user.data.UserID

interface EligibleState {

    fun <T : UserEligible<T>> register(newEmail: Email, newUserId: UserID, eligible: T): T

    fun <T : UserEligible<T>> suspend(eligible: T): T

    fun <T : UserEligible<T>> reactivate(eligible: T): T

}