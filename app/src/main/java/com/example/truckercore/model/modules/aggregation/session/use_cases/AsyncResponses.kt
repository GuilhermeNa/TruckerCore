package com.example.truckercore.model.modules.aggregation.session.use_cases

import com.example.truckercore._utils.classes.AppResponse
import com.example.truckercore._utils.classes.AppResult
import com.example.truckercore.model.modules.aggregation.session.data.Session
import com.example.truckercore.model.modules.company.data.Company
import com.example.truckercore.model.modules.user._contracts.UserEligible
import kotlinx.coroutines.Deferred

data class AsyncResponses(
    val companyDef: Deferred<AppResponse<Company>>,
    val eligibleDef: Deferred<AppResponse<UserEligible<*>>>
) {

    suspend inline fun awaitAndMap(
        block: (AppResponse<Company>, AppResponse<UserEligible<*>>) -> AppResult<Session>
    ): AppResult<Session> {
        val companyResponse = companyDef.await()
        val eligibleResponse = eligibleDef.await()
        return block(companyResponse, eligibleResponse)
    }

}