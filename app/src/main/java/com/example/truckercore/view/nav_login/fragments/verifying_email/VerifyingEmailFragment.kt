package com.example.truckercore.view.nav_login.fragments.verifying_email

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import com.example.truckercore._shared.expressions.doIfResumedOrElse
import com.example.truckercore._shared.expressions.logState
import com.example.truckercore.databinding.FragmentVerifyingEmailBinding
import com.example.truckercore.view._shared._base.fragments.CloseAppFragment
import com.example.truckercore.view._shared.expressions.launchOnFragmentLifecycle
import com.example.truckercore.view_model.view_models.verifying_email.VerifyingEmailViewModel
import com.example.truckercore.view_model.view_models.verifying_email.state.VerifyingEmailStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class VerifyingEmailFragment : CloseAppFragment(), BottomSheetVerifyingEmailListener {

    // View Binding
    private var _binding: FragmentVerifyingEmailBinding? = null
    val binding get() = _binding!!

    // Fragment Handlers
    private val stateHandler by lazy { VerifyingEmailUiStateHandler() }

    // Transition Listener
    private val transitionListener = object : MotionLayout.TransitionListener {
        override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
           // viewModel.transitionEnd()
        }

        override fun onTransitionStarted(motionLayout: MotionLayout?, startId: Int, endId: Int) {}
        override fun onTransitionChange(motionLayout: MotionLayout?, startId: Int, endId: Int, progress: Float) {}
        override fun onTransitionTrigger(motionLayout: MotionLayout?, triggerId: Int, positive: Boolean, progress: Float) {}
    }

    // ViewModel
    private val viewModel: VerifyingEmailViewModel by viewModel()

    //----------------------------------------------------------------------------------------------
    // On Create
    //----------------------------------------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        doOnInitialization(savedInstanceState) { viewModel.initialize() }
        launchOnFragmentLifecycle {
            setStateManager(savedInstanceState)
            setEffectManager()
            setCounterStateManager()
        }
    }

    private fun CoroutineScope.setStateManager(savedInstanceState: Bundle?) = launch {
        viewModel.stateFLow.collect { state ->
            logState(this@VerifyingEmailFragment, state)
           // stateHandler.handleComponent(state.emailComponent)
            handleStatus(savedInstanceState, state.status)
        }
    }

    private fun handleStatus(savedInstanceState: Bundle?, state: VerifyingEmailStatus) {
    /*    when (state) {
            VerifyingEmailStatus.Idle -> Unit

            VerifyingEmailStatus.TimeOut -> {
                if (childFragmentManager.findFragmentByTag(BottomSheetVerifyingEmail.TAG) == null) {
                    BottomSheetVerifyingEmail().show(
                        childFragmentManager,
                        BottomSheetVerifyingEmail.TAG
                    )
                }
            }

            VerifyingEmailStatus.Verified -> {
                doIfRecreatingView(savedInstanceState) { stateHandler.jumpToEnd() }
            }
        }*/
    }

    private fun CoroutineScope.setEffectManager() = launch {
        /*    viewModel.effectFlow.collect { effect ->
                logEffect(this@VerifyingEmailFragment, effect)
                when (effect) {
                    VerifyingEmailEffect.UiEffect.NavigateToNotification -> {
                        val direction = VerifyingEmailFragmentDirections
                            .actionVerifyingEmailFragmentToUserNameFragment()
                        navigateToDirection(direction)
                    }

                    VerifyingEmailEffect.UiEffect.NavigateToUserName -> {
                        val direction = VerifyingEmailFragmentDirections
                            .actionVerifyingEmailFragmentToUserNameFragment()
                        navigateToDirection(direction)
                    }

                    VerifyingEmailEffect.UiEffect.TransitionToEnd -> {
                        stateHandler.transitionToEnd()
                    }

                    else -> Unit
                }
            }*/
        }

    private suspend fun setCounterStateManager() {
        viewModel.counterFlow.collect { value ->
            doIfResumedOrElse(
                resumed = { stateHandler.animateProgress(value) },
                orElse = { stateHandler.jumpToProgress(value) }
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
       // binding.fragVerifyingEmailMotionLayout.addTransitionListener(transitionListener)
    }

    //----------------------------------------------------------------------------------------------
    // On Destroy View
    //----------------------------------------------------------------------------------------------
    override fun onDestroyView() {
        super.onDestroyView()
        removeTransitionListener()
        _binding = null
    }

    private fun removeTransitionListener() {
      //  binding.fragVerifyingEmailMotionLayout.removeTransitionListener(transitionListener)
    }

    //----------------------------------------------------------------------------------------------
    // BottomSheetListener
    //----------------------------------------------------------------------------------------------
    override fun onRetry() {
        viewModel.retry()
    }

    override fun onChangeEmail() {
        viewModel.createAnotherEmail()
    }

/*    val direction = VerifyingEmailFragmentDirections
        .actionGlobalEmailAuthFragment()
    navigateToDirection(direction)*/
}

