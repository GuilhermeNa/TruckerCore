package com.example.truckercore.view.nav_login.fragments.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.navigation.NavDirections
import com.example.truckercore._shared.expressions.doIfCritical
import com.example.truckercore._shared.expressions.doIfRecreating
import com.example.truckercore._shared.expressions.logEffect
import com.example.truckercore._shared.expressions.logState
import com.example.truckercore._shared.expressions.navigateToActivity
import com.example.truckercore._shared.expressions.navigateToDirection
import com.example.truckercore.databinding.FragmentSplashBinding
import com.example.truckercore.model.configs.flavor.FlavorService
import com.example.truckercore.view._shared._base.fragments.CloseAppFragment
import com.example.truckercore.view._shared.expressions.launchOnFragment
import com.example.truckercore.view._shared.views.activities.NotificationActivity
import com.example.truckercore.view_model.view_models.splash.effect.SplashEffect
import com.example.truckercore.view_model.view_models.splash.event.SplashEvent
import com.example.truckercore.view_model.view_models.splash.state.SplashState
import com.example.truckercore.view_model.view_models.splash.SplashViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : CloseAppFragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SplashViewModel by viewModel()

    private val flavorService: FlavorService by inject()

    private var stateHandler: SplashUiStateHandler? = null

    private val transitionListener = object : MotionLayout.TransitionListener {
        override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
            val event = when (currentId) {
                stateHandler?.loadingUiState -> SplashEvent.UiEvent.TransitionToLoadingComplete
                stateHandler?.navigationUiState -> SplashEvent.UiEvent.TransitionToNavigationComplete
                else -> throw NotImplementedError(
                    "SplashFragment completed transition listener unimplemented with id: $currentId."
                )
            }
            viewModel.onEvent(event)
        }

        override fun onTransitionStarted(motionLayout: MotionLayout?, startId: Int, endId: Int) {}

        override fun onTransitionChange(
            motionLayout: MotionLayout?, startId: Int, endId: Int, progress: Float
        ) {
        }

        override fun onTransitionTrigger(
            motionLayout: MotionLayout?, triggerId: Int, positive: Boolean, progress: Float
        ) {
        }
    }

    //----------------------------------------------------------------------------------------------
    // On Create
    //----------------------------------------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        launchOnFragment {
            setStateManager()
            setEffectManager()
        }
    }

    private fun CoroutineScope.setStateManager() {
        launch {
            viewModel.uiState.collect { state ->
                logState(this@SplashFragment, state)

                when (state) {
                    SplashState.Initial -> {
                        stateHandler?.bindAppName(flavorService.getAppName())
                    }

                    SplashState.Loading -> doIfRecreating {
                        stateHandler?.bindAppName(flavorService.getAppName())
                        stateHandler?.jumpToLoadingState()
                    }

                    is SplashState.Navigating -> {
                        doIfRecreating {
                            stateHandler?.bindAppName(flavorService.getAppName())
                            stateHandler?.jumpToNavigationState()
                        }
                        navigateToDirection(getNavDirection(state))
                    }

                    is SplashState.Error -> state.uiError.doIfCritical {
                        val intent = NotificationActivity.newInstance(context = requireContext())
                        navigateToActivity(intent, true)
                    }

                }
            }
        }
    }

    private fun getNavDirection(state: SplashState.Navigating): NavDirections {
        return when (state) {
            SplashState.Navigating.Welcome -> {
                SplashFragmentDirections.actionSplashFragmentToWelcomeFragment()
            }

            SplashState.Navigating.Login -> {
                SplashFragmentDirections.actionSplashFragmentToLoginFragment()
            }

            SplashState.Navigating.PreparingAmbient -> {
                TODO()
            }

            SplashState.Navigating.ContinueRegister -> {
                SplashFragmentDirections.actionGlobalContinueRegisterFragment()
            }
        }
    }

    private suspend fun setEffectManager() {
        viewModel.effect.collect { effect ->
            logEffect(this@SplashFragment, effect)

            when (effect) {
                SplashEffect.TransitionToLoading -> stateHandler?.transitionToLoadingState()
                SplashEffect.TransitionToNavigation -> stateHandler?.transitionToNavigationState()
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
        stateHandler = SplashUiStateHandler(
            motionLayout = binding.motionLayout,
            textView = binding.fragSplashName
        )
        return binding.root
    }

    //----------------------------------------------------------------------------------------------
    // On View Created
    //----------------------------------------------------------------------------------------------
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //navigateToActivity(MainActivity::class.java, true)
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
        stateHandler = null
        _binding = null
    }

}
