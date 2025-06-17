package com.example.truckercore.view.nav_login.fragments.splash

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import com.example.truckercore._shared.expressions.doIfRecreating
import com.example.truckercore._shared.expressions.logEffect
import com.example.truckercore._shared.expressions.logState
import com.example.truckercore._shared.expressions.navigateToDirection
import com.example.truckercore.databinding.FragmentSplashBinding
import com.example.truckercore.view._shared._base.fragments.CloseAppFragment
import com.example.truckercore.view._shared.expressions.launchOnFragment
import com.example.truckercore.view._shared.views.activities.NotificationActivity
import com.example.truckercore.view_model.view_models.splash.effect.SplashEffect
import com.example.truckercore.view_model.view_models.splash.SplashViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : CloseAppFragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SplashViewModel by viewModel()

    private var stateHandler = SplashUiStateHandler()

    private val transitionListener = object : MotionLayout.TransitionListener {
        override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
            when (currentId) {
                stateHandler.loadingUiState -> viewModel.notifyLoadingTransitionEnd()
                stateHandler.loadedUiState -> viewModel.notifyLoadedTransitionEnd()
                else -> throw NotImplementedError("$UI_STATE_ERROR_MSG $currentId.")
            }
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

    private companion object {
        private const val UI_STATE_ERROR_MSG =
            "SplashFragment completed transition listener unimplemented with id:"
    }

    //----------------------------------------------------------------------------------------------
    // On Create
    //----------------------------------------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initialize()
        launchOnFragment {
            setStateManager()
            setEffectManager()
        }
    }

    private fun CoroutineScope.setStateManager() {
        launch {
            viewModel.stateFlow.collect { state ->
                logState(this@SplashFragment, state)
                stateHandler.handleNameComponent(state.nameComponent)
                 doIfRecreating { stateHandler.handleStatus(state.status) }
            }
        }
    }

    private suspend fun setEffectManager() {
        viewModel.effectFlow.collect { effect ->
            logEffect(this@SplashFragment, effect)
            when(effect) {
                is SplashEffect.Transition -> stateHandler.handleTransition(effect)
                is SplashEffect.Navigate -> handleNavigation(effect)
            }
        }
    }

    private fun handleNavigation(effect: SplashEffect.Navigate) {
        when(effect) {
            SplashEffect.Navigate.ToContinue -> {
                val direction = SplashFragmentDirections.actionGlobalContinueRegisterFragment()
                navigateToDirection(direction)
            }
            SplashEffect.Navigate.ToLogin -> {
                val direction = SplashFragmentDirections.actionSplashFragmentToLoginFragment()
                navigateToDirection(direction)
            }
            SplashEffect.Navigate.ToMain -> {
                val direction = SplashFragmentDirections.actionSplashFragmentToLoginFragment()
                navigateToDirection(direction)
            }
            SplashEffect.Navigate.ToNotification -> {
                val intent = Intent(requireActivity(), NotificationActivity::class.java)
                requireActivity().startActivity(intent)
                requireActivity().finish()
            }
            SplashEffect.Navigate.ToWelcome -> {
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
    // On Destroy View
    //----------------------------------------------------------------------------------------------
    override fun onDestroyView() {
        super.onDestroyView()
        binding.motionLayout.removeTransitionListener(transitionListener)
        _binding = null
    }

}
