package com.example.truckercore.view.fragments.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.truckercore._utils.expressions.doIfRecreating
import com.example.truckercore._utils.expressions.getName
import com.example.truckercore.databinding.FragmentSplashBinding
import com.example.truckercore.model.configs.flavor.FlavorService
import com.example.truckercore.model.logger.AppLogger
import com.example.truckercore.view.fragments._base.CloseAppFragment
import com.example.truckercore.view_model.view_models.splash.SplashEffect
import com.example.truckercore.view_model.view_models.splash.SplashEvent
import com.example.truckercore.view_model.view_models.splash.SplashUiState
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
    private val navigationHandler by lazy { SplashNavigationHandler() }

    private val transitionListener = object : MotionLayout.TransitionListener {
        override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
            val event = when (currentId) {
                stateHandler?.loadingUiState -> SplashEvent.UiEvent.TransitionToLoadingComplete
                stateHandler?.navigationUiState -> SplashEvent.UiEvent.TransitionToNavigation
                else -> throw NotImplementedError(
                    "SplashFragment completed transition listener unimplemented with id: $currentId."
                )
            }
            viewModel.onEvent(event)
        }

        // ---> UNUSED <--- //
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
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                setUiStateManager()
                setEffectManager()
            }
        }
    }

    private fun CoroutineScope.setUiStateManager() {
        launch {
            viewModel.uiState.collect { state ->
                AppLogger.d(this@SplashFragment.getName(), "State: $state")

                when (state) {
                    SplashUiState.Initial -> {
                        stateHandler?.bindAppName(flavorService.getAppName())
                    }

                    SplashUiState.Loading -> doIfRecreating {
                        stateHandler?.bindAppName(flavorService.getAppName())
                        stateHandler?.jumpToLoadingState()
                    }

                    SplashUiState.Navigating -> {
                        doIfRecreating {
                            stateHandler?.bindAppName(flavorService.getAppName())
                            stateHandler?.jumpToNavigationState()
                        }

                    }
                }
                /*  when (state) {
                  is SplashUiState.Initial -> ifResumedOrElse(
                      resumed = {
                          // Expected flow: the user opens the app and waits for the animation to finish.
                          // The animation listener will notify the ViewModel that
                          // the FirstAnim event was completed.
                          stateHandler?.bindAppName(flavorService.getAppName())
                          stateHandler?.transitionToSecondState()
                      },
                      orElse = {
                          // Unexpected flow: the user opens the app and closes it before
                          // the first transition is completed.
                          // The FirstAnim event must be triggered outside the animation listener.
                          viewModel.onEvent(SplashEvent.UiEvent.TransitionToLoadingComplete)
                      }
                  )

                  is SplashUiState.Loading -> {
                      // Intermediate state. This ensures that the view is correctly recreated
                      // when the user leaves and returns to the fragment while in the loading state.
                      if (lifecycle.currentState != Lifecycle.State.RESUMED) {
                          stateHandler?.bindAppName(flavorService.getAppName())
                          stateHandler?.jumpToSecondState()
                      }
                  }

                  is SplashUiState.Loaded -> {
                      ifResumedOrElse(
                          resumed = {
                              // Expected flow: after the data is loaded, the app runs the second
                              // animation, and the listener marks it as complete in the ViewModel.
                              stateHandler?.transitionToThirdState()
                          },
                          orElse = {
                              // Unexpected flow: the user closes the app before the second transition is completed.
                              // The SecondAnim event must be triggered outside the animation listener.
                              stateHandler?.bindAppName(flavorService.getFlavor().appName)
                              stateHandler?.jumpToThirdState()
                              viewModel.onEvent(SplashEvent.UiEvent.SecondAnimComplete)
                          }
                      )
                  }
              }*/
            }
        }
    }

    private suspend fun setEffectManager() {
        viewModel.effect.collect { effect ->
            AppLogger.d(this@SplashFragment.getName(), "Effect: $effect")

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
