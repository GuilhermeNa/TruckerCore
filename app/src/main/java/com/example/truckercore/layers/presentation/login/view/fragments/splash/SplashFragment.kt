package com.example.truckercore.layers.presentation.login.view.fragments.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.truckercore.core.config.flavor.FlavorService
import com.example.truckercore.core.my_lib.expressions.getTag
import com.example.truckercore.core.my_lib.expressions.lockOrientationPortrait
import com.example.truckercore.core.my_lib.expressions.navigateToDirection
import com.example.truckercore.core.my_lib.expressions.unlockOrientation
import com.example.truckercore.databinding.FragmentSplashBinding
import com.example.truckercore.infra.logger.AppLogger
import com.example.truckercore.layers.presentation.base.abstractions.view._public.PublicLockedFragment
import com.example.truckercore.layers.presentation.login.view_model.splash.SplashViewModel
import com.example.truckercore.layers.presentation.login.view_model.splash.helpers.SplashEffect
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : PublicLockedFragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SplashViewModel by viewModel()
    private val flavorService: FlavorService by inject()

    private var stateHandler = SplashFragmentUiStateHandler()

    private val transitionListener = object : MotionLayout.TransitionListener {

        override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
            when (currentId) {
                stateHandler.loadingUiState -> viewModel.toLoadingTransitionEnd()
                stateHandler.loadedUiState -> viewModel.toLoadedTransitionEnd()
                else -> handleUiStateError(currentId)
            }
        }

        private fun handleUiStateError(currentId: Int) {
            AppLogger.e(tag = getTag, msg = "Unexpected id for transition: $currentId.")
            navigateToErrorActivity(requireActivity())
        }

        override fun onTransitionStarted(motionLayout: MotionLayout?, startId: Int, endId: Int) {}

        override fun onTransitionChange(
            motionLayout: MotionLayout?,
            startId: Int,
            endId: Int,
            progress: Float
        ) {
        }

        override fun onTransitionTrigger(
            motionLayout: MotionLayout?,
            triggerId: Int,
            positive: Boolean,
            progress: Float
        ) {
        }

    }

    //----------------------------------------------------------------------------------------------
    // On Create
    //----------------------------------------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                setStateManager(savedInstanceState)
                setEffectManager()
            }
        }
    }

    private fun CoroutineScope.setStateManager(savedInstanceState: Bundle?) {
        launch {
            viewModel.stateFlow.collect { state ->
                stateHandler.handleNameComponent(state.nameComponent)
                onRecreatingView(savedInstanceState) {
                    stateHandler.handleStatus(state.status)
                }
            }
        }
    }

    private suspend fun setEffectManager() {
        viewModel.effectFlow.collect { effect ->
            when (effect) {
                is SplashEffect.UiEffect.Transition -> stateHandler.handleTransition(effect)
                is SplashEffect.UiEffect.Navigation -> handleNavigation(effect)
                else -> Unit
            }
        }
    }

    private fun handleNavigation(effect: SplashEffect.UiEffect.Navigation) {
        when (effect) {
            SplashEffect.UiEffect.Navigation.ToContinue -> {
                val direction = SplashFragmentDirections.actionGlobalContinueRegisterFragment()
                navigateToDirection(direction)
            }

            SplashEffect.UiEffect.Navigation.ToLogin -> {
                val direction = SplashFragmentDirections.actionSplashFragmentToLoginFragment()
                navigateToDirection(direction)
            }

            SplashEffect.UiEffect.Navigation.ToCheckIn -> flavorService.navigateToCheckIn(
                requireActivity()
            )

            SplashEffect.UiEffect.Navigation.ToNotification -> navigateToErrorActivity(
                requireActivity()
            )

            SplashEffect.UiEffect.Navigation.ToWelcome -> {
                val direction = SplashFragmentDirections.actionSplashFragmentToWelcomeFragment()
                navigateToDirection(direction)
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
        _binding = FragmentSplashBinding.inflate(layoutInflater)
        stateHandler.initialize(binding)
        return binding.root
    }

    //----------------------------------------------------------------------------------------------
    // On View Created
    //----------------------------------------------------------------------------------------------
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTransitionListener()
    }

    private fun setTransitionListener() {
        binding.motionLayout.setTransitionListener(transitionListener)
    }

    //----------------------------------------------------------------------------------------------
    // On Resume
    //----------------------------------------------------------------------------------------------
    override fun onResume() {
        super.onResume()
        lockOrientationPortrait()
    }

    //----------------------------------------------------------------------------------------------
    // On Destroy View
    //----------------------------------------------------------------------------------------------
    override fun onDestroyView() {
        super.onDestroyView()
        unlockOrientation()
        binding.motionLayout.removeTransitionListener(transitionListener)
        _binding = null
    }

}
