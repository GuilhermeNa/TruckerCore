package com.example.truckercore.model.modules.user._contracts

import com.example.truckercore._utils.classes.Email
import com.example.truckercore.model.configs.annotations.InternalUseOnly
import com.example.truckercore.model.modules.user._contracts.eligible_state.EligibleData
import com.example.truckercore.model.modules.user._contracts.eligible_state.EligibleState
import com.example.truckercore.model.modules.user.data.UserID

interface UserEligible<T : UserEligible<T>> {

    val userId: UserID?

    val state: EligibleState

    @InternalUseOnly
    fun setState(data: EligibleData, newState: EligibleState): T

    fun registerUser(newEmail: Email, newUserId: UserID): T

    fun suspendUserRegister(): T

    fun reactivateUserRegister(): T

}

