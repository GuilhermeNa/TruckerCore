package com.example.truckercore.business_admin.layers.presentation.check_in.view_model.helpers

import com.example.truckercore.layers.presentation.base.contracts.Event

sealed class CheckInEvent : Event {

    data object Initialize: CheckInEvent()

    sealed class Retry : CheckInEvent() {
        data object CheckAccess : Retry()
        data object CreateAccess : Retry()
    }

    sealed class CheckAccessTask : CheckInEvent() {
        data object Registered : CheckAccessTask()
        data object Unregistered : CheckAccessTask()
        data object NoConnection : CheckAccessTask()
        data object Failure : CheckAccessTask()
    }

    sealed class CreateAccessTask : CheckInEvent() {
        data object Complete : CreateAccessTask()
        data object NoConnection : CreateAccessTask()
        data object Failure : CreateAccessTask()
    }

    val isReconnectionRequest get() = isCheckageConnectionError || isCreationConnectionError

    val isCheckageConnectionError get() = this is CheckAccessTask.NoConnection

    val isCreationConnectionError get() = this is CreateAccessTask.NoConnection

    val isAccessUnregistered get() = this is CheckAccessTask.Unregistered

}