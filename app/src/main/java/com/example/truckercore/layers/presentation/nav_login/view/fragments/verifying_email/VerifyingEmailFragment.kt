package com.example.truckercore.layers.presentation.nav_login.view.fragments.verifying_email

import  android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.truckercore.core.my_lib.expressions.launchAndRepeatOnFragmentStartedLifeCycle
import com.example.truckercore.core.my_lib.expressions.navigateToDirection
import com.example.truckercore.databinding.FragmentVerifyingEmailBinding
import com.example.truckercore.layers.presentation.base.abstractions.view._public.PublicLockedFragment
import com.example.truckercore.layers.presentation.nav_login.view_model.verifying_email.VerifyingEmailViewModel
import com.example.truckercore.layers.presentation.nav_login.view_model.verifying_email.effect.VerifyingEmailFragmentEffect
import com.example.truckercore.layers.presentation.nav_login.view_model.verifying_email.event.VerifyingEmailFragmentEvent
import com.example.truckercore.layers.presentation.nav_login.view_model.verifying_email.state.VerifyingEmailFragmentState
import com.example.truckercore.presentation.nav_login.fragments.verifying_email.VerifyingEmailFragmentDirections
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class VerifyingEmailFragment : PublicLockedFragment() {

    // View Binding
    private var _binding: FragmentVerifyingEmailBinding? = null
    val binding get() = _binding!!

    // ViewModel
    private val viewModel: VerifyingEmailViewModel by viewModel()
    private val stateHandler = VerifyingEmailFragmentStateHandler()

    //----------------------------------------------------------------------------------------------
    // On Create
    //----------------------------------------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        launchAndRepeatOnFragmentStartedLifeCycle {
            setFragmentStateManager()
            setCounterStateManager(savedInstanceState)
            setFragmentEffectManager()
        }
    }

    private fun CoroutineScope.setFragmentStateManager() = launch {
        viewModel.stateFLow.collect { state ->
            handleEmail()
            handleState(state)
        }
    }

    private fun handleEmail() {
        viewModel.email?.let(stateHandler::bindEmail)
    }

    private fun handleState(state: VerifyingEmailFragmentState) {
        stateHandler.handleState(state, ::transitionEnd)
    }

    private fun transitionEnd() {
        viewModel.onEvent(VerifyingEmailFragmentEvent.VerifiedUiTransitionEnd)
    }

    private fun CoroutineScope.setCounterStateManager(instanceState: Bundle?) = launch {
        viewModel.counterFlow.collect { value ->
            onFragmentUiState(
                instanceState = instanceState,
                resumed = { stateHandler.animateProgress(value) },
                recreating = { stateHandler.jumpToProgress(value) }
            )
        }
    }

    private suspend fun setFragmentEffectManager() {
        // Scope val's
        val profileDirection = VerifyingEmailFragmentDirections
            .actionVerifyingEmailFragmentToUserNameFragment()

        val loginDirection = VerifyingEmailFragmentDirections
            .actionGlobalLoginFragment()

        // Effect manager
        viewModel.effectFlow.collect { effect ->
            when (effect) {
                VerifyingEmailFragmentEffect.Navigation.ToProfile ->
                    navigateToDirection(profileDirection)

                VerifyingEmailFragmentEffect.Navigation.ToLogin ->
                    navigateToDirection(loginDirection)

                VerifyingEmailFragmentEffect.Navigation.ToNoConnection ->
                    navigateToNoConnection(this) {
                        viewModel.onEvent(VerifyingEmailFragmentEvent.RetryTask)
                    }

                VerifyingEmailFragmentEffect.Navigation.ToNotification ->
                    navigateToErrorActivity(requireActivity())

                else -> Unit
            }
        }
    }

    //----------------------------------------------------------------------------------------------
    // On Create View
    //----------------------------------------------------------------------------------------------
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVerifyingEmailBinding.inflate(layoutInflater)
        stateHandler.initialize(binding)
        return binding.root
    }

    //----------------------------------------------------------------------------------------------
    // On View Created
    //----------------------------------------------------------------------------------------------
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Button CLick listener
        setSendButtonClickListener()
        setNewEmailClickListener()
    }

    //----------------------------------------------------------------------------------------------
    // On Destroy View
    //----------------------------------------------------------------------------------------------
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

