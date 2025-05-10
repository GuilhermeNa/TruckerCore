package com.example.truckercore.model.modules.user._contracts.eligible_state

import com.example.truckercore.model.modules.user._contracts.UserEligible

interface EligibleState {

    fun <T : UserEligible<T>> register(data: EligibleData, eligible: T): T

    fun <T : UserEligible<T>> suspend(data: EligibleData, eligible: T): T

    fun <T : UserEligible<T>> reactivate(data: EligibleData, eligible: T): T

}