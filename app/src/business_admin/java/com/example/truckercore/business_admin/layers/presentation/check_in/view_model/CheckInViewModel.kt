package com.example.truckercore.business_admin.layers.presentation.check_in.view_model

import androidx.lifecycle.viewModelScope
import com.example.truckercore.business_admin.layers.domain.use_case.company.InitializeCompanyAccessUseCase
import com.example.truckercore.business_admin.layers.presentation.check_in.view_model.helpers.CheckInEffect
import com.example.truckercore.business_admin.layers.presentation.check_in.view_model.helpers.CheckInEvent
import com.example.truckercore.business_admin.layers.presentation.check_in.view_model.helpers.CheckInReducer
import com.example.truckercore.business_admin.layers.presentation.check_in.view_model.helpers.CheckInRetryReason
import com.example.truckercore.business_admin.layers.presentation.check_in.view_model.helpers.CheckInRetryReason.CHECKING_ACCESS
import com.example.truckercore.business_admin.layers.presentation.check_in.view_model.helpers.CheckInRetryReason.CREATING_ACCESS
import com.example.truckercore.business_admin.layers.presentation.check_in.view_model.helpers.CheckInState
import com.example.truckercore.business_admin.layers.presentation.check_in.view_model.mappers.toCheckAccessEvent
import com.example.truckercore.business_admin.layers.presentation.check_in.view_model.mappers.toCreateAccessEvent
import com.example.truckercore.business_admin.layers.presentation.check_in.view_model.mappers.toRetryEvent
import com.example.truckercore.business_admin.layers.presentation.check_in.view_model.objects.CurrentUser
import com.example.truckercore.core.error.PresentationException
import com.example.truckercore.core.my_lib.expressions.get
import com.example.truckercore.core.my_lib.expressions.getTag
import com.example.truckercore.core.my_lib.expressions.handle
import com.example.truckercore.infra.logger.AppLogger
import com.example.truckercore.infra.logger.Logable
import com.example.truckercore.layers.domain.use_case.authentication.GetUidUseCase
import com.example.truckercore.layers.domain.use_case.authentication.GetUserEmailUseCase
import com.example.truckercore.layers.domain.use_case.authentication.GetUserNameUseCase
import com.example.truckercore.layers.domain.use_case.user.CheckDomainUserRegisteredUseCase
import com.example.truckercore.layers.presentation.base.abstractions.view_model.BaseViewModel
import com.example.truckercore.layers.presentation.base.managers.EffectManager
import com.example.truckercore.layers.presentation.base.managers.StateManager
import kotlinx.coroutines.launch

class CheckInViewModel(
    private val checkDomainUserRegisteredUseCase: CheckDomainUserRegisteredUseCase,
    private val initializeCompanyAccessUseCase: InitializeCompanyAccessUseCase,
    private val getUidUseCase: GetUidUseCase,
    private val getNameUseCase: GetUserNameUseCase,
    private val getEmailUseCase: GetUserEmailUseCase
) : BaseViewModel(){

    private val stateManager = StateManager<CheckInState>(CheckInState.Loading)
    val stateFlow = stateManager.stateFlow

    private val effectManager = EffectManager<CheckInEffect>()
    val effectFlow = effectManager.effectFlow

    private val reducer = CheckInReducer()

    private val currentUser: CurrentUser by lazy {
        CurrentUser(
            uid = getUidUseCase().get(),
            name = getNameUseCase().get(),
            email = getEmailUseCase().get()
        )
    }

    private var _retryReason: CheckInRetryReason? = null
    private val retryReason
        get() = _retryReason ?: run {
            throw PresentationException.Unknown(INVALID_RETRY_REASON)
        }

    // ---------------------------------------------------------------------------------------------
    // Event Dispatcher
    // ---------------------------------------------------------------------------------------------
    private fun onEvent(newEvent: CheckInEvent) {
        val result = reducer.reduce(stateManager.currentState(), newEvent)
        result.handle(stateManager::update, ::handleEffect)
    }

    private fun handleEffect(effect: CheckInEffect) {
        when (effect) {
            CheckInEffect.LaunchCheckAccessTask -> checkAccessTask()
            CheckInEffect.LaunchCreateAccessTask -> createAccessTask()
            is CheckInEffect.Navigation -> effectManager.trySend(effect)
        }
    }

    // ---------------------------------------------------------------------------------------------
    // Check Company Access Task
    // ---------------------------------------------------------------------------------------------
    private fun checkAccessTask() {
        viewModelScope.launch {
            val outcome = checkDomainUserRegisteredUseCase(currentUser.uid)
            val newEvent = outcome.toCheckAccessEvent()
            verifyReconnectionRequest(newEvent)
            onEvent(newEvent)
        }
    }

    // ---------------------------------------------------------------------------------------------
    // Create Company Access Task
    // ---------------------------------------------------------------------------------------------
    private fun createAccessTask() = viewModelScope.launch {
        val outcome = initializeCompanyAccessUseCase(
            currentUser.uid, currentUser.name, currentUser.email
        )
        val newEvent = outcome.toCreateAccessEvent()
        verifyReconnectionRequest(newEvent)
        onEvent(newEvent)
    }

    //----------------------------------------------------------------------------------------------
    // Other Methods
    //----------------------------------------------------------------------------------------------
    private fun verifyReconnectionRequest(newEvent: CheckInEvent) = with(newEvent) {
        _retryReason = when {
            isCheckageConnectionError -> CHECKING_ACCESS
            isCreationConnectionError -> CREATING_ACCESS
            else -> return
        }
    }

    // ---------------------------------------------------------------------------------------------
    // Public API
    // ---------------------------------------------------------------------------------------------
    fun initialize() {
        onEvent(CheckInEvent.Initialize)
    }

    fun retry() {
        val newEvent = retryReason.toRetryEvent()
        _retryReason = null
        onEvent(newEvent)
    }

    private companion object {
        private const val INVALID_RETRY_REASON =
            "Retry reason should not be null when retrying on CheckInViewModel."
    }

}