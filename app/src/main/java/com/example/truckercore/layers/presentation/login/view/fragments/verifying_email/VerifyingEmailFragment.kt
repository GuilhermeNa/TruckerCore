package com.example.truckercore.layers.presentation.login.view.fragments.verifying_email

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.truckercore.core.my_lib.expressions.launchAndRepeatOnFragmentStartedLifeCycle
import com.example.truckercore.core.my_lib.expressions.navigateToDirection
import com.example.truckercore.databinding.FragmentVerifyingEmailBinding
import com.example.truckercore.layers.presentation.base.abstractions.view.public.PublicLockedFragment
import com.example.truckercore.layers.presentation.common.LoadingDialog
import com.example.truckercore.layers.presentation.login.view_model.verifying_email.VerifyingEmailViewModel
import com.example.truckercore.layers.presentation.login.view_model.verifying_email.helpers.VerifyingEmailFragmentEffect
import com.example.truckercore.layers.presentation.login.view_model.verifying_email.helpers.VerifyingEmailFragmentEvent
import com.example.truckercore.layers.presentation.login.view_model.verifying_email.helpers.VerifyingEmailFragmentState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Fragment responsible for handling the email verification flow.
 *
 * This screen displays the user's email, allows resending the verification email,
 * shows a countdown timer for re-sending availability, and navigates to the
 * appropriate next step based on the verification result.
 *
 * It observes state and effects exposed by [VerifyingEmailViewModel] and updates
 * its UI using [VerifyingEmailFragmentStateHandler].
 */
class VerifyingEmailFragment : PublicLockedFragment() {

    private var _binding: FragmentVerifyingEmailBinding? = null
    val binding: FragmentVerifyingEmailBinding get() = _binding!!

    private val viewModel: VerifyingEmailViewModel by viewModel()

    private var dialog: LoadingDialog? = null

    /** Handler responsible for all UI updates based on fragment state. */
    private val stateHandler = VerifyingEmailFragmentStateHandler()

    // ---------------------------------------------------------------------------------------------
    // Lifecycle: onCreate
    // ---------------------------------------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initializes the ViewModel with instance state when needed
        onInitializing(savedInstanceState, viewModel::initialize)

        // Observes state, counter and effect flows only when the fragment is at least STARTED.
        launchAndRepeatOnFragmentStartedLifeCycle {
            setFragmentStateManager()
            setCounterStateManager(savedInstanceState)
            setFragmentEffectManager()
        }
    }

    /**
     * Collects state updates from the ViewModel and delegates UI handling.
     */
    private fun CoroutineScope.setFragmentStateManager() = launch {
        viewModel.stateFLow.collect { state ->
            handleEmail()
            handleState(state)
        }
    }

    /**
     * Binds the user email to the UI (only once or when changed).
     */
    private fun handleEmail() {
        viewModel.email?.let(stateHandler::bindEmail)
    }

    /**
     * Routes the current fragment state to the UI handler.
     */
    private fun handleState(state: VerifyingEmailFragmentState) {
        stateHandler.handleState(state, dialog, ::transitionEnd)
    }

    /**
     * Called after the UI finishes its transition animation when email is verified.
     */
    private fun transitionEnd() {
        viewModel.onEvent(VerifyingEmailFragmentEvent.VerifiedUiTransitionEnd)
    }

    /**
     * Collects countdown timer values and updates the progress bar accordingly.
     *
     * @param instanceState Used to determine if the fragment is being recreated.
     */
    private fun CoroutineScope.setCounterStateManager(instanceState: Bundle?) = launch {
        viewModel.counterFlow.collect { value ->
            onFragmentUiState(
                instanceState = instanceState,
                resumed = { stateHandler.updateProgress(value, true) },
                recreating = { stateHandler.updateProgress(value, false) }
            )
        }
    }

    /**
     * Handles one-off effects emitted by the ViewModel such as navigation.
     */
    private suspend fun setFragmentEffectManager() {
        // Pre-defined navigation directions
        val profileDirection = VerifyingEmailFragmentDirections
            .actionVerifyingEmailFragmentToUserNameFragment()

        val loginDirection = VerifyingEmailFragmentDirections
            .actionGlobalLoginFragment()

        val newEmailDirection = VerifyingEmailFragmentDirections
            .actionGlobalEmailAuthFragment()

        // Effect collector
        viewModel.effectFlow.collect { effect ->
            when (effect) {

                VerifyingEmailFragmentEffect.Navigation.ToProfile ->
                    navigateToDirection(profileDirection)

                VerifyingEmailFragmentEffect.Navigation.ToNewEmail ->
                    navigateToDirection(newEmailDirection)

                VerifyingEmailFragmentEffect.Navigation.ToLogin ->
                    navigateToDirection(loginDirection)

                VerifyingEmailFragmentEffect.Navigation.ToNoConnection ->
                    navigateToNoConnection(this) {
                        viewModel.onEvent(VerifyingEmailFragmentEvent.RetryTask)
                    }

                VerifyingEmailFragmentEffect.Navigation.ToNotification ->
                    navigateToErrorActivity(requireActivity())

                else -> Unit
            }
        }
    }

    // ---------------------------------------------------------------------------------------------
    // Lifecycle: onCreateView
    // ---------------------------------------------------------------------------------------------
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVerifyingEmailBinding.inflate(layoutInflater)
        stateHandler.initialize(binding)
        return binding.root
    }

    // ---------------------------------------------------------------------------------------------
    // Lifecycle: onViewCreated
    // ---------------------------------------------------------------------------------------------
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog = LoadingDialog(requireContext())
        setSendButtonClickListener()
        setNewEmailClickListener()
    }

    /**
     * Handles click on “Use another email” button.
     */
    private fun setNewEmailClickListener() {
        binding.fragVerifyingEmailButtonNewEmail.setOnClickListener {
            viewModel.onEvent(VerifyingEmailFragmentEvent.Click.NewEmailButton)
        }
    }

    /**
     * Handles click on “Send verification email” button.
     */
    private fun setSendButtonClickListener() {
        binding.fragVerifyingEmailButtonSend.setOnClickListener {
            viewModel.onEvent(VerifyingEmailFragmentEvent.Click.SendEmailButton)
        }
    }

    // ---------------------------------------------------------------------------------------------
    // Lifecycle: onDestroyView
    // ---------------------------------------------------------------------------------------------
    override fun onDestroyView() {
        super.onDestroyView()
        dialog?.dismiss()
        dialog = null
        _binding = null
    }

}
