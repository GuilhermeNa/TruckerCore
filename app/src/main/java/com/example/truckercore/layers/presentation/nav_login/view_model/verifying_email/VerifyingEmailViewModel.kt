package com.example.truckercore.layers.presentation.nav_login.view_model.verifying_email

import androidx.lifecycle.ViewModel
import com.example.truckercore.core.my_lib.classes.Email
import com.example.truckercore.core.my_lib.expressions.handle
import com.example.truckercore.layers.data.base.outcome.expressions.get
import com.example.truckercore.layers.data.base.outcome.expressions.isEmpty
import com.example.truckercore.layers.data.base.outcome.expressions.isSuccess
import com.example.truckercore.layers.domain.use_case.authentication.GetUserEmailUseCase
import com.example.truckercore.layers.presentation.base.managers.EffectManager
import com.example.truckercore.layers.presentation.base.managers.StateManager
import com.example.truckercore.layers.presentation.nav_login.view_model.verifying_email.effect.VerifyingEmailFragmentEffect
import com.example.truckercore.layers.presentation.nav_login.view_model.verifying_email.event.VerifyingEmailEvent
import com.example.truckercore.layers.presentation.nav_login.view_model.verifying_email.event.VerifyingEmailFragmentEvent
import com.example.truckercore.layers.presentation.nav_login.view_model.verifying_email.reducer.VerifyingEmailFragmentReducer
import com.example.truckercore.layers.presentation.nav_login.view_model.verifying_email.state.VerifyingEmailFragmentState
import com.example.truckercore.layers.presentation.nav_login.view_model.verifying_email.use_cases.SendVerificationEmailViewUseCase
import com.example.truckercore.layers.presentation.nav_login.view_model.verifying_email.use_cases.VerifyEmailViewUseCase

class VerifyingEmailViewModel(
    private val getUserEmailUseCase: GetUserEmailUseCase,
    private val sendEmailUseCase: SendVerificationEmailViewUseCase,
    private val verifyEmailUseCase: VerifyEmailViewUseCase
) : ViewModel() {

    private var _email: Email? = null
    val email get() = _email

    val counterFlow = verifyEmailUseCase.counterFlow

    private val stateManager =
        StateManager<VerifyingEmailFragmentState>(VerifyingEmailFragmentState.Initial)
    val stateFLow = stateManager.stateFlow

    private val effectManager = EffectManager<VerifyingEmailFragmentEffect>()
    val effectFlow = effectManager.effectFlow

    private val reducer = VerifyingEmailFragmentReducer()

    //----------------------------------------------------------------------------------------------
    fun initialize() {
        val result = getUserEmailUseCase()

        val newEvent = when {
            result.isSuccess() -> {
                _email = result.get()
                VerifyingEmailFragmentEvent.GetEmailTask.Complete
            }

            result.isEmpty() -> VerifyingEmailFragmentEvent.GetEmailTask.Empty

            else -> VerifyingEmailFragmentEvent.GetEmailTask.Failure
        }

        onEvent(newEvent)
    }

    private fun onEvent(newEvent: VerifyingEmailFragmentEvent) {
        val result = reducer.reduce(stateManager.currentState(), newEvent)
        result.handle(stateManager::update, ::handleEffect)
    }

    private fun handleEffect(newEffect: VerifyingEmailFragmentEffect) {

    }


    fun retry() {
        onEvent(OnRetryCLicked)
    }

    fun createAnotherEmail() {
        onEvent(OnCreateNewEmailClicked)
    }

}