package com.example.truckercore.business_admin.layers.presentation.main.activity.view_model

import com.example.truckercore.business_admin.layers.domain.use_case.session.ObserveSessionUseCase
import com.example.truckercore.layers.domain.singletons.session.SessionState
import com.example.truckercore.layers.presentation.base.abstractions.view_model.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SessionViewModel(
    private val observeSessionUseCase: ObserveSessionUseCase
) : BaseViewModel() {

    private val _session = MutableStateFlow(SessionState.Loading)
    val sessionFLow get() = _session.asStateFlow()



}