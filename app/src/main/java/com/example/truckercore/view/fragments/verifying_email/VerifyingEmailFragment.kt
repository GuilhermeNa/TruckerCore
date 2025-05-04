package com.example.truckercore.view.fragments.verifying_email

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.truckercore.databinding.FragmentVerifyingEmailBinding
import com.example.truckercore.model.shared.utils.expressions.handleOnUi
import com.example.truckercore.model.shared.utils.expressions.logError
import com.example.truckercore.view.activities.NotificationActivity
import com.example.truckercore.view.dialogs.BottomSheetVerifyingEmail
import com.example.truckercore.view.expressions.navigateTo
import com.example.truckercore.view.expressions.onLifecycleState
import com.example.truckercore.view_model.view_models.verifying_email.VerifyingEmailEffect
import com.example.truckercore.view_model.view_models.verifying_email.VerifyingEmailEvent
import com.example.truckercore.view_model.view_models.verifying_email.VerifyingEmailUiState
import com.example.truckercore.view_model.view_models.verifying_email.VerifyingEmailViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class VerifyingEmailFragment : Fragment() {

    // View Binding
    private var _binding: FragmentVerifyingEmailBinding? = null
    val binding get() = _binding!!

    // Fragment Handlers
    private var _stateHandler: VerifyingEmailUiStateHandler? = null
    private val stateHandler get() = _stateHandler!!

    // Bottom Sheet
    private val bottomSheet by lazy {
        fun retry() {
            viewModel.onEvent(VerifyingEmailEvent.UiEvent.RetryVerification)
        }

        fun navigateToEmailAuth() {
            val direction = VerifyingEmailFragmentDirections
                .actionVerifyingEmailFragmentToEmailAuthFragment()
            navigateTo(direction)
        }

        BottomSheetVerifyingEmail(
            onRetry = { retry() },
            onChangeEmail = {
                viewModel.resetUserRegistration()
                navigateToEmailAuth()
            }
        )
    }

    // Transition Listener
    private val transitionListener = object : MotionLayout.TransitionListener {
        override fun onTransitionStarted(
            motionLayout: MotionLayout?, startId: Int, endId: Int
        ) {}

        override fun onTransitionChange(
            motionLayout: MotionLayout?, startId: Int, endId: Int, progress: Float
        ) {}

        override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
       /*     val direction = VerifyingEmailFragmentDirections
                .actionVerifyingEmailFragmentToPreparingAmbientFragment()
            navigateTo(direction)*/
        }

        override fun onTransitionTrigger(
            motionLayout: MotionLayout?, triggerId: Int, positive: Boolean, progress: Float
        ) {}

    }

    // ViewModel
    private val viewModel: VerifyingEmailViewModel by viewModel()

    //----------------------------------------------------------------------------------------------
    // On Create
    //----------------------------------------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.onEvent(VerifyingEmailEvent.UiEvent.StartVerification)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                setFragmentStateManager()
                setFragmentEffectManager()
                setCounterStateManager()
            }
        }
    }

    private fun CoroutineScope.setFragmentStateManager() {
        launch {
            viewModel.state.collect { state ->
                stateHandler.render(state)
                if (state is VerifyingEmailUiState.TimeOut) {
                    bottomSheet.show(
                        requireActivity().supportFragmentManager,
                        BottomSheetVerifyingEmail.TAG
                    )
                }
            }
        }
    }

    private fun CoroutineScope.setFragmentEffectManager() {
        launch {
            viewModel.effect.collect { effect ->
                if (effect is VerifyingEmailEffect.Error) {
                    effect.error.errorCode.handleOnUi(
                        onRecoverable = { message -> logError(message) },
                        onFatalError = { name, message ->
                            val intent = NotificationActivity.newInstance(
                                context = requireContext(),
                                errorHeader = name,
                                errorBody = message
                            )
                            startActivity(intent)
                            requireActivity().finish()
                        }
                    )
                }
            }
        }
    }

    private suspend fun setCounterStateManager() {
        viewModel.counterFlow.collect { value ->
            onLifecycleState(
                resumed = { stateHandler.incrementProgress(value, true) },
                anyOther = { stateHandler.incrementProgress(value, false) }
            )
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
        _stateHandler = VerifyingEmailUiStateHandler(viewModel.getEmail().value, binding)
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
        binding.fragVerifyingEmailMotionLayout.addTransitionListener(transitionListener)
    }

    //----------------------------------------------------------------------------------------------
    // On Destroy View
    //----------------------------------------------------------------------------------------------

    override fun onDestroyView() {
        super.onDestroyView()
        removeTransitionListener()
        _stateHandler = null
        _binding = null
    }

    private fun removeTransitionListener() {
        binding.fragVerifyingEmailMotionLayout.removeTransitionListener(transitionListener)
    }

}

