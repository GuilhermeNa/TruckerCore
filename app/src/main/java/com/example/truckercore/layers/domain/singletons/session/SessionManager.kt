package com.example.truckercore.layers.domain.singletons.session

import com.example.truckercore.business_admin.layers.domain.use_case.session.ObserveSessionUseCase
import com.example.truckercore.core.my_lib.expressions.foldRequired
import com.example.truckercore.layers.data.base.outcome.DataOutcome
import com.example.truckercore.layers.domain.model.session.Session
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class SessionManager(
    scope: CoroutineScope,
    useCase: ObserveSessionUseCase
) {

    val sessionFLow: StateFlow<SessionState> =
        useCase()
            .map(::sessionState)
            .stateIn(
                scope = scope,
                started = SharingStarted.Eagerly,
                initialValue = SessionState.Loading
            )

    private fun sessionState(it: DataOutcome<Session>) =
        it.foldRequired(
            onSuccess = { session -> SessionState.Success(session) },
            orElse = { error -> SessionState.Error(error) },
        )

    fun companyId() = (sessionFLow.value as SessionState.Success).session.companyId()

}