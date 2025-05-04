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
import com.example.truckercore.R
import com.example.truckercore.databinding.FragmentSplashBinding
import com.example.truckercore.view.activities.NotificationActivity
import com.example.truckercore.view.expressions.animPumpAndDump
import com.example.truckercore.view.expressions.onLifecycleState
import com.example.truckercore.view_model.view_models.splash.SplashEffect
import com.example.truckercore.view_model.view_models.splash.SplashEvent
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
        ) {}

        override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
            val event =
                if (currentId == R.id.frag_verifying_email_state_2)
                    SplashEvent.UiEvent.FirstAnimComplete
                else SplashEvent.UiEvent.SecondAnimComplete

            viewModel.onEvent(event)
        }

        override fun onTransitionTrigger(
            motionLayout: MotionLayout?, triggerId: Int,
            positive: Boolean, progress: Float
        ) {}
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
                onLifecycleState(
                    resumed = { stateHandler?.handleUiTransition(state, true) },
                    creating = {
                        stateHandler?.bindAppName(state)
                        stateHandler?.handleUiTransition(state, false)
                    }
                )
            }
        }
    }

    private suspend fun setEffectManager() {
        viewModel.effect.collect { effect ->
            when (effect) {
                SplashEffect.FirstTimeAccess -> TODO()
                SplashEffect.AlreadyAccessed.RequireLogin -> TODO()
                SplashEffect.AlreadyAccessed.AuthenticatedUser.AwaitingRegistration -> TODO()
                SplashEffect.AlreadyAccessed.AuthenticatedUser.RegistrationCompleted -> TODO()
                is SplashEffect.Error -> TODO()
            }
        }
    }

    private fun handleInitialState() {
        setMotionLayoutCompletedListener {
            viewModel.run()
        }
    }

    private fun handleFirstAccess() {
        /*  val flavor = requireContext().getFlavor()
          val direction = BaSplashFragmentDirections.actionSplashFragmentToWelcomeFragment(flavor)
          navigateTo(direction)*/
    }

    private fun handleLoggedUser(state: UserLoggedIn) {
        when (state) {
            is UserLoggedIn.SystemAccessAllowed -> navigateToMainActivity()
            is UserLoggedIn.SystemAccessDenied -> navigateToDeniedSystemAccessFragment()
            is UserLoggedIn.ProfileIncomplete -> navigateToProfileCreationFragment()
        }
    }

    private fun handleUserNotFound() {
        navigateToLoginFragment()
    }

    private fun handleError(error: Exception) {
        navigateToNotificationActivityWithAnError(error)
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

    private fun navigateToNotificationActivityWithAnError(error: Exception) {
        val intent = NotificationActivity.newInstance(
            context = requireContext(),
            gifRes = R.drawable.gif_error,
            errorHeader = viewModel.getErrorTitle(),
            errorBody = viewModel.getErrorMessage()
        )
        startActivity(intent)
        requireActivity().finish()
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
