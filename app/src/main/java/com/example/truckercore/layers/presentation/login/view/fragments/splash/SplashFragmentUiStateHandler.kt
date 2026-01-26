package com.example.truckercore.layers.presentation.login.view.fragments.splash

import com.example.truckercore.R
import com.example.truckercore.databinding.FragmentSplashBinding
import com.example.truckercore.layers.presentation.base.handlers.StateHandler
import com.example.truckercore.core.my_lib.ui_components.TextComponent
import com.example.truckercore.layers.presentation.login.view_model.splash.helpers.SplashEffect
import com.example.truckercore.layers.presentation.login.view_model.splash.helpers.SplashStatus

/**
 * Responsible for handling MotionLayout transitions and binding UI elements
 * related to the splash screen's visual states.
 *
 * This class encapsulates all UI logic related to animations and text,
 * keeping the Fragment clean and focused on state handling.
 */
class SplashFragmentUiStateHandler : StateHandler<FragmentSplashBinding>() {

    // Ui States
    private val initialUiState = R.id.frag_verifying_email_state_1
    val loadingUiState = R.id.frag_verifying_email_state_2
    val loadedUiState = R.id.frag_verifying_email_state_3

    fun handleNameComponent(nameComponent: TextComponent) {
        bindText(nameComponent, binding.fragSplashName)
    }

    fun handleStatus(status: SplashStatus) {
        val state = getState(status)
        state?.let { binding.motionLayout.jumpToState(it) }
    }

    private fun getState(status: SplashStatus) =
        when (status) {
            SplashStatus.Initial -> null
            SplashStatus.Loading -> loadingUiState
            SplashStatus.Loaded -> loadedUiState
        }

    fun handleTransition(transitionEffect: SplashEffect.UiEffect.Transition) {
        val transition = getTransition(transitionEffect)
        binding.motionLayout.transitionToState(transition)
    }

    private fun getTransition(transitionEffect: SplashEffect.UiEffect.Transition) =
        when (transitionEffect) {
            SplashEffect.UiEffect.Transition.ToLoading -> loadingUiState
            SplashEffect.UiEffect.Transition.ToLoaded -> loadedUiState
        }

}

