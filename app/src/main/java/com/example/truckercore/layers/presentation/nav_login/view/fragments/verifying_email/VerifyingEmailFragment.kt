package com.example.truckercore.layers.presentation.nav_login.view.fragments.verifying_email

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import com.example.truckercore.databinding.FragmentVerifyingEmailBinding
import com.example.truckercore.layers.presentation.base.abstractions.view._public.PublicLockedFragment
import com.example.truckercore.layers.presentation.nav_login.view_model.verifying_email.VerifyingEmailViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class VerifyingEmailFragment : PublicLockedFragment(), BottomSheetVerifyingEmailListener {

    // View Binding
    private var _binding: FragmentVerifyingEmailBinding? = null
    val binding get() = _binding!!

    // ViewModel
    private val viewModel: VerifyingEmailViewModel by viewModel()
    private val stateHandler = VerifyingEmailUiStateHandler()

    // Transition Listener
    private val transitionListener = object : MotionLayout.TransitionListener {
        override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
            // viewModel.transitionEnd()
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
        launchOnFragmentLifecycle {
            setStateManager(savedInstanceState)
            setEffectManager()
            setCounterStateManager()
        }
    }

    private fun CoroutineScope.setStateManager(savedInstanceState: Bundle?) = launch {
        viewModel.stateFLow.collect { state ->
            logState(this@VerifyingEmailFragment, state)
            stateHandler.handleState(state)
        }
    }

    private fun CoroutineScope.setEffectManager() = launch {
        viewModel.effectFlow.collect { effect ->
            logEffect(this@VerifyingEmailFragment, effect)
            when (effect) {
                com.example.truckercore.presentation.viewmodels.view_models.verifying_email.effect.VerifyingEmailUiEffect.NavigateToCreateNameFragment -> {
                    val direction = VerifyingEmailFragmentDirections
                        .actionVerifyingEmailFragmentToUserNameFragment()
                    navigateToDirection(direction)
                }

                com.example.truckercore.presentation.viewmodels.view_models.verifying_email.effect.VerifyingEmailUiEffect.NavigateToCreateNewEmailFragment -> {
                    val direction = VerifyingEmailFragmentDirections
                        .actionGlobalEmailAuthFragment()
                    navigateToDirection(direction)
                }

                com.example.truckercore.presentation.viewmodels.view_models.verifying_email.effect.VerifyingEmailUiEffect.NavigateToErrorActivity -> {
                    val intent = NotificationActivity.newInstance(requireContext())
                    navigateToActivity(intent, true)
                }

                com.example.truckercore.presentation.viewmodels.view_models.verifying_email.effect.VerifyingEmailUiEffect.NavigateToNoConnectionFragment -> {
                    setNoConnectionListener()
                    val direction = VerifyingEmailFragmentDirections
                        .actionGlobalNoConnectionFragmentNavLogin()
                    navigateToDirection(direction)
                }

                com.example.truckercore.presentation.viewmodels.view_models.verifying_email.effect.VerifyingEmailUiEffect.ShowBottomSheet -> {
                    com.example.truckercore.layers.presentation.nav_login.view.fragments.verifying_email.BottomSheetVerifyingEmail()
                        .show(
                            childFragmentManager,
                            com.example.truckercore.layers.presentation.nav_login.view.fragments.verifying_email.BottomSheetVerifyingEmail.Companion.TAG
                        )
                }
            }
        }
    }

    private suspend fun setCounterStateManager() {
        viewModel.counterFlow.collect { value ->
            doIfResumedOrElse(
                resumed = { stateHandler.animateProgress(value) },
                orElse = { stateHandler.jumpToProgress(value) }
            )
        }
    }

    private fun setNoConnectionListener() {
        NoConnectionFragment.setResultListener(
            parentFragmentManager,
            viewLifecycleOwner
        ) { connected ->
            if (connected) viewModel.retry()
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

}

