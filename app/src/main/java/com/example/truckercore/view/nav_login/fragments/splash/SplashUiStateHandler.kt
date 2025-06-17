package com.example.truckercore.view.nav_login.fragments.splash

import com.example.truckercore.R
import com.example.truckercore.databinding.FragmentSplashBinding
import com.example.truckercore.view._shared._base.handlers.StateHandler
import com.example.truckercore.view_model._shared.components.TextComponent
import com.example.truckercore.view_model.view_models.splash.effect.SplashEffect
import com.example.truckercore.view_model.view_models.splash.state.SplashStatus

/**
 * Responsible for handling MotionLayout transitions and binding UI elements
 * related to the splash screen's visual states.
 *
 * This class encapsulates all UI logic related to animations and text,
 * keeping the Fragment clean and focused on state handling.
 */
class SplashUiStateHandler : StateHandler<FragmentSplashBinding>() {

    // Ui States
    private val initialUiState = R.id.frag_verifying_email_state_1
    val loadingUiState = R.id.frag_verifying_email_state_2
    val loadedUiState = R.id.frag_verifying_email_state_3

    fun handleNameComponent(nameComponent: TextComponent) {
        bindText(nameComponent, getBinding().fragSplashName)
    }

    fun handleStatus(status: SplashStatus) {
        val state = when (status) {
            SplashStatus.Initial -> null
            SplashStatus.Loading -> loadingUiState
            SplashStatus.Loaded -> loadedUiState
        }
        state?.let { getBinding().motionLayout.jumpToState(it) }

    }

    fun handleTransition(transitionEffect: SplashEffect.Transition) {
        val transition = when (transitionEffect) {
            SplashEffect.Transition.ToLoading -> loadingUiState
            SplashEffect.Transition.ToLoaded -> loadedUiState
        }
        getBinding().motionLayout.transitionToState(transition)
    }

}

