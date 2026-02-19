package com.example.truckercore.business_admin.layers.presentation.check_in.view_model.mappers

import com.example.truckercore.business_admin.layers.presentation.check_in.view_model.helpers.CheckInEvent
import com.example.truckercore.business_admin.layers.presentation.check_in.view_model.helpers.CheckInRetryReason
import com.example.truckercore.business_admin.layers.presentation.check_in.view_model.helpers.CheckInRetryReason.CHECKING_ACCESS
import com.example.truckercore.core.my_lib.expressions.getOrNull
import com.example.truckercore.core.my_lib.expressions.isByNetwork
import com.example.truckercore.core.my_lib.expressions.isConnectionError
import com.example.truckercore.core.my_lib.expressions.map
import com.example.truckercore.layers.data.base.outcome.DataOutcome
import com.example.truckercore.layers.data.base.outcome.OperationOutcome

//--------------------------------------------------------------------------------------------------
// Check Access Event Mapper
//--------------------------------------------------------------------------------------------------
fun DataOutcome<Boolean>.toCheckAccessEvent(): CheckInEvent.CheckAccessTask {
    val data = getOrNull()
    return data?.let(::mapSuccess) ?: mapEmptyOrError(this)
}

private fun mapSuccess(registered: Boolean) =
    if (registered) CheckInEvent.CheckAccessTask.Registered
    else CheckInEvent.CheckAccessTask.Unregistered

private fun mapEmptyOrError(outcome: DataOutcome<Boolean>) =
    if (outcome is DataOutcome.Failure) {
        if (outcome.isConnectionError()) {
            CheckInEvent.CheckAccessTask.NoConnection
        } else CheckInEvent.CheckAccessTask.Failure
    } else CheckInEvent.CheckAccessTask.Unregistered

//--------------------------------------------------------------------------------------------------
// Create Access Event Mapper
//--------------------------------------------------------------------------------------------------
fun OperationOutcome.toCreateAccessEvent() = map(
    onComplete = { CheckInEvent.CreateAccessTask.Complete },
    onFailure = {
        if (it.isByNetwork()) {
            CheckInEvent.CreateAccessTask.NoConnection
        } else CheckInEvent.CreateAccessTask.Failure
    }
)

//--------------------------------------------------------------------------------------------------
// Retry Event Mapper
//--------------------------------------------------------------------------------------------------
fun CheckInRetryReason.toRetryEvent() =
    if (this == CHECKING_ACCESS) {
        CheckInEvent.Retry.CheckAccess
    } else CheckInEvent.Retry.CreateAccess