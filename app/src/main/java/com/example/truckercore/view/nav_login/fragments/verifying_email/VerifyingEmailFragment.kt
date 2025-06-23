package com.example.truckercore.view.nav_login.fragments.verifying_email

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import com.example.truckercore._shared.expressions.doIfResumedOrElse
import com.example.truckercore._shared.expressions.logState
import com.example.truckercore._shared.expressions.navigateToDirection
import com.example.truckercore.databinding.FragmentVerifyingEmailBinding
import com.example.truckercore.view._shared._base.fragments.CloseAppFragment
import com.example.truckercore.view._shared.expressions.launchOnFragment
import com.example.truckercore.view_model.view_models.verifying_email.event.VerifyingEmailEvent
import com.example.truckercore.view_model.view_models.verifying_email.state.VerifyingEmailState
import com.example.truckercore.view_model.view_models.verifying_email.VerifyingEmailViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class VerifyingEmailFragment : CloseAppFragment(), BottomSheetVerifyingEmailListener {

    // View Binding
    private var _binding: FragmentVerifyingEmailBinding? = null
    val binding get() = _binding!!

    // Fragment Handlers
    private var stateHandler: VerifyingEmailUiStateHandler? = null

    // Bottom Sheet
    private val bottomSheet by lazy { BottomSheetVerifyingEmail() }

    // Transition Listener
    private val transitionListener = object : MotionLayout.TransitionListener {
        override fun onTransitionStarted(
            motionLayout: MotionLayout?, startId: Int, endId: Int
        ) {
        }

        override fun onTransitionChange(
            motionLayout: MotionLayout?, startId: Int, endId: Int, progress: Float
        ) {
        }

        override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
            val direction = VerifyingEmailFragmentDirections
                .actionVerifyingEmailFragmentToUserNameFragment()
            navigateToDirection(direction)
        }

        override fun onTransitionTrigger(
            motionLayout: MotionLayout?, triggerId: Int, positive: Boolean, progress: Float
        ) {
        }

    }

    // ViewModel
    private val viewModel: VerifyingEmailViewModel by viewModel()

    //----------------------------------------------------------------------------------------------
    // On Create
    //----------------------------------------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        launchOnFragment {
            setFragmentStateManager()
            setCounterStateManager()
        }
    }

    private fun CoroutineScope.setFragmentStateManager() = launch {
        viewModel.state.collect { state ->
            logState(this@VerifyingEmailFragment, state)
            stateHandler?.render(state)
            handleState(state)
        }
    }

    private fun handleState(state: VerifyingEmailState) {
        if (state is VerifyingEmailState.TimeOut && !bottomSheet.alreadyShown())
            bottomSheet.show(
                childFragmentManager,
                BottomSheetVerifyingEmail.TAG
            )
    }

    private suspend fun setCounterStateManager() {
        viewModel.counterFlow.collect { value ->
            doIfResumedOrElse(
                resumed = { stateHandler?.animateProgress(value) },
                orElse = { stateHandler?.jumpToProgress(value) }
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
        stateHandler = VerifyingEmailUiStateHandler(viewModel.getEmail().value, binding)
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
        stateHandler = null
        _binding = null
    }

    private fun removeTransitionListener() {
        binding.fragVerifyingEmailMotionLayout.removeTransitionListener(transitionListener)
    }

    //----------------------------------------------------------------------------------------------
    // BottomSheetListener
    //----------------------------------------------------------------------------------------------
    override fun onRetry() {
        viewModel.onEvent(VerifyingEmailEvent.UiEvent.RetryVerification)
    }

    override fun onChangeEmail() {
        val direction = VerifyingEmailFragmentDirections
            .actionGlobalEmailAuthFragment()
        navigateToDirection(direction)
    }

}

