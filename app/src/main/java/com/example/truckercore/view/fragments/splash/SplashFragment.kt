package com.example.truckercore.view.fragments.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.truckercore.databinding.FragmentSplashBinding
import com.example.truckercore.view.activities.NotificationActivity
import com.example.truckercore.view.expressions.animPumpAndDump
import com.example.truckercore.view.expressions.onLifecycleState
import com.example.truckercore.view_model.view_models.splash.SplashEffect
import com.example.truckercore.view_model.view_models.splash.SplashEvent
import com.example.truckercore.view_model.view_models.splash.SplashUiState
import com.example.truckercore.view_model.view_models.splash.SplashViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SplashViewModel by viewModel()

    private var stateHandler: SplashUiStateHandler? = null

    private val transitionListener = object : MotionLayout.TransitionListener {
        override fun onTransitionStarted(motionLayout: MotionLayout?, startId: Int, endId: Int) {}

        override fun onTransitionChange(
            motionLayout: MotionLayout?, startId: Int,
            endId: Int, progress: Float
        ) {
        }

        override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
            val event = when (currentId) {
                stateHandler?.secondUiState -> SplashEvent.UiEvent.FirstAnimComplete
                else -> SplashEvent.UiEvent.SecondAnimComplete
            }
            viewModel.onEvent(event)
        }

        override fun onTransitionTrigger(
            motionLayout: MotionLayout?, triggerId: Int,
            positive: Boolean, progress: Float
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
                when (state) {
                    is SplashUiState.Initial -> {
                        onLifecycleState(
                            resumed = {
                                // Expected flow: the user opens the app and waits for the animation to finish.
                                // The animation listener will notify the ViewModel that
                                // the FirstAnim event was completed.
                                stateHandler?.bindAppName(state.flavor)
                                stateHandler?.runFirstUiTransition()
                            },
                            anyOther = {
                                // Unexpected flow: the user opens the app and closes it before
                                // the first transition is completed.
                                // The FirstAnim event must be triggered outside the animation listener.
                                viewModel.onEvent(SplashEvent.UiEvent.FirstAnimComplete)
                            }
                        )
                    }

                    is SplashUiState.Loading -> {
                        // Intermediate state. This ensures that the view is correctly recreated
                        // when the user leaves and returns to the fragment while in the loading state.
                        if (lifecycle.currentState != Lifecycle.State.RESUMED) {
                            stateHandler?.bindAppName(state.flavor)
                            stateHandler?.jumpToSecondUiState()
                        }
                    }

                    is SplashUiState.Loaded -> {
                        onLifecycleState(
                            resumed = {
                                // Expected flow: after the data is loaded, the app runs the second
                                // animation, and the listener marks it as complete in the ViewModel.
                                stateHandler?.runSecondUiTransition()
                            },
                            anyOther = {
                                // Unexpected flow: the user closes the app before the second transition is completed.
                                // The SecondAnim event must be triggered outside the animation listener.
                                stateHandler?.bindAppName(state.flavor)
                                stateHandler?.jumpToThirdUiState()
                                viewModel.onEvent(SplashEvent.UiEvent.SecondAnimComplete)
                            }
                        )
                    }
                }
            }
        }
    }

    private suspend fun setEffectManager() {
        viewModel.effect.collect { effect ->
            when (effect) {
                SplashEffect.FirstTimeAccess -> {
                    TODO("Continuar a implementar as navegações")
                }

                SplashEffect.AlreadyAccessed.RequireLogin -> {
                    TODO("Continuar a implementar as navegações")
                }

                SplashEffect.AlreadyAccessed.AuthenticatedUser.AwaitingRegistration -> {
                    TODO("Continuar a implementar as navegações")
                }

                SplashEffect.AlreadyAccessed.AuthenticatedUser.RegistrationCompleted -> {
                    TODO("Continuar a implementar as navegações")
                }

                is SplashEffect.Error -> {
                    val intent = NotificationActivity.newInstance(
                        context = requireContext(),
                        errorHeader = effect.error.name,
                        errorBody = effect.error.userMessage
                    )
                    startActivity(intent)
                    requireActivity().finish()
                }
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

    private fun setMotionLayoutCompletedListener(complete: () -> Unit) {
        binding.motionLayout.setTransitionListener(
            object : MotionLayout.TransitionListener {
                override fun onTransitionStarted(
                    motionLayout: MotionLayout?,
                    startId: Int,
                    endId: Int
                ) {
                }

                override fun onTransitionChange(
                    motionLayout: MotionLayout?,
                    startId: Int,
                    endId: Int,
                    progress: Float
                ) {
                }

                override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                    complete()
                }

                override fun onTransitionTrigger(
                    motionLayout: MotionLayout?,
                    triggerId: Int,
                    positive: Boolean,
                    progress: Float
                ) {
                }

            }
        )
    }

    private suspend fun removeLoadingBar() {
        binding.fragSplashProgressbar.animPumpAndDump()
        delay(600)
    }

    private fun navigateToWelcomeFragment() {}

    private fun navigateToLoginFragment() {}

    private fun navigateToProfileCreationFragment() {}

    private fun navigateToMainActivity() {

    }

    private fun navigateToDeniedSystemAccessFragment() {

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
