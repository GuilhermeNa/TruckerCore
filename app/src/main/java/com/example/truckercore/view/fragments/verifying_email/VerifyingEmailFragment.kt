package com.example.truckercore.view.fragments.verifying_email

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.example.truckercore.R
import com.example.truckercore.business_admin.view.activities.HomeActivity
import com.example.truckercore.databinding.FragmentVerifyingEmailBinding
import com.example.truckercore.model.shared.utils.expressions.handleOnUi
import com.example.truckercore.model.infrastructure.app_exception.ErrorCode
import com.example.truckercore.view.activities.NotificationActivity
import com.example.truckercore.view.expressions.executeOnState
import com.example.truckercore.view.expressions.navigateTo
import com.example.truckercore.view.expressions.showSnackBarRed
import com.example.truckercore.view.expressions.showToast
import com.example.truckercore.view.fragments.base.CloseAppFragment
import com.example.truckercore.view_model.view_models.verifying_email.VerifyingEmailEffect.CounterReachZero
import com.example.truckercore.view_model.view_models.verifying_email.VerifyingEmailEffect.EmailVerificationFailed
import com.example.truckercore.view_model.view_models.verifying_email.VerifyingEmailEffect.EmailVerificationSucceed
import com.example.truckercore.view_model.view_models.verifying_email.VerifyingEmailEffect.SendEmailFailed
import com.example.truckercore.view_model.view_models.verifying_email.VerifyingEmailEffect.SendEmailSucceed
import com.example.truckercore.view_model.view_models.verifying_email.VerifyingEmailEvent.NewAccountButtonClicked
import com.example.truckercore.view_model.view_models.verifying_email.VerifyingEmailEvent.ResendButtonClicked
import com.example.truckercore.view_model.view_models.verifying_email.VerifyingEmailFragState.TryingToVerify
import com.example.truckercore.view_model.view_models.verifying_email.VerifyingEmailFragState.WaitingResend
import com.example.truckercore.view_model.view_models.verifying_email.VerifyingEmailViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Fragment responsible for verifying the user's email after account creation.
 *
 * This screen displays a timer and manages the email verification process,
 * including resending the verification email and reacting to the result.
 *
 * Features:
 * - Countdown timer to prevent immediate resends
 * - UI state transitions depending on verification status
 * - Handles effects such as success/failure messages and navigation
 * - Integrates tightly with ViewModel using State/Event/Effect patterns
 */
class VerifyingEmailFragment : CloseAppFragment() {

    // View Binding
    private var _binding: FragmentVerifyingEmailBinding? = null
    val binding get() = _binding!!

    // Fragment Handlers
    private val stateHandler by lazy { StateHandler() }
    private val eventHandler by lazy { EventHandler() }
    private val effectHandler by lazy { EffectHandler() }

    // Navigation Arguments
    val args: VerifyingEmailFragmentArgs by navArgs()

    // ViewModel
    private val viewModel: VerifyingEmailViewModel by viewModel()

    // Fragment Lifecycle - onCreate ---------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Observes ViewModel state/effect/event streams during the STARTED lifecycle state
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                setFragmentStateManager()
                setCounterStateManager()
                setEffectManager()
                setEventManager()
            }
        }
    }

    /**
     * Collects the ViewModel state and delegates visual updates to [StateHandler].
     */
    private fun CoroutineScope.setFragmentStateManager() {
        launch {
            viewModel.state.collect { state ->
                when (state) {
                    is TryingToVerify -> stateHandler.tryingToVerify()
                    is WaitingResend -> stateHandler.waitingResend()
                }
            }
        }
    }

    /**
     * Observes countdown value from ViewModel and updates the timer UI.
     * Triggers effect when counter reaches zero.
     */
    private fun CoroutineScope.setCounterStateManager() {
        launch {
            viewModel.counter.collect { counter ->
                stateHandler.updateCounterUiValue(counter)
                if (counter == 0) viewModel.setEffect(CounterReachZero)
            }
        }
    }

    /**
     * Collects one-time effects from the ViewModel and applies them through [EffectHandler].
     */
    private fun CoroutineScope.setEffectManager() {
        launch {
            viewModel.effect.collect { effect ->
                when (effect) {
                    is CounterReachZero -> effectHandler.counterReachZero()
                    is EmailVerificationSucceed -> effectHandler.emailVerificationSucceed()
                    is EmailVerificationFailed -> effectHandler.handleErrorCode(effect.errorCode)
                    is SendEmailFailed -> effectHandler.handleErrorCode(effect.errorCode)
                    is SendEmailSucceed -> effectHandler.sendEmailSucceed(effect.message)
                }
            }
        }
    }

    /**
     * Observes user interaction events from ViewModel and handles them via [EventHandler].
     */
    private suspend fun setEventManager() {
        viewModel.event.collect { event ->
            when (event) {
                ResendButtonClicked -> eventHandler.resendButtonClicked()
                NewAccountButtonClicked -> eventHandler.newAccountButtonClicked()
            }
        }
    }

    // Fragment Lifecycle - onCreateView -----------------------------------------------------------
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVerifyingEmailBinding.inflate(layoutInflater)
        return binding.root
    }

    // Fragment Lifecycle - onViewCreated ----------------------------------------------------------
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setResendButtonClickListener()
        setNewAccountButtonCLickListener()
    }

    /**
     * Sets the resend button click listener, dispatching a [ResendButtonClicked] event to ViewModel.
     */
    private fun setResendButtonClickListener() {
        binding.fragVerifyingEmailButtonResend.setOnClickListener {
            viewModel.setEvent(ResendButtonClicked)
        }
    }

    /**
     * Sets the new account button click listener, dispatching a [NewAccountButtonClicked] event to ViewModel.
     */
    private fun setNewAccountButtonCLickListener() {
        binding.fragVerifyingEmailButtonNewAccount.setOnClickListener {
            viewModel.setEvent(NewAccountButtonClicked)
        }
    }

    // Fragment Lifecycle - onDestroyView ----------------------------------------------------------
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // ---------------------------------------------------------------------------------------------
    // Internal Classes for Handling Logic
    // ---------------------------------------------------------------------------------------------
    /**
     * Handles one-time effects emitted by the ViewModel.
     */
    private inner class EffectHandler {

        /**
         * Triggered when the countdown timer reaches zero.
         * Enables resend functionality.
         */
        fun counterReachZero() = viewModel.setState(WaitingResend)

        /**
         * Called when email verification succeeds.
         * Navigates the user to the Home screen.
         */
        fun emailVerificationSucceed() {
            requireActivity().navigateTo(HomeActivity::class.java)
            requireActivity().finish()
        }

        /**
         * Handles both recoverable and fatal errors using the provided [ErrorCode].
         */
        fun handleErrorCode(errorCode: ErrorCode) = errorCode.handleOnUi(
            recoverable = { showSnackBarRed(errorCode.userMessage) },
            fatalError = { navigateToNotificationActivity(errorCode) }
        )

        private fun navigateToNotificationActivity(errorCode: ErrorCode) {
            val intent = NotificationActivity.newInstance(
                context = requireContext(),
                gifRes = R.drawable.gif_error,
                errorHeader = errorCode.name,
                errorBody = errorCode.userMessage
            )
            startActivity(intent)
            requireActivity().finish()
        }

        /**
         * Called when the resend email action succeeds.
         * Shows a toast and resets UI to 'verifying' state.
         */
        fun sendEmailSucceed(message: String) {
            showToast(message)
            viewModel.setState(TryingToVerify)
        }

    }

    /**
     * Handles UI events triggered by the user.
     */
    private inner class EventHandler {

        /**
         * Triggers the resend verification email logic.
         */
        fun resendButtonClicked() {
            viewModel.sendVerificationEmail()
        }

        /**
         * Navigates the user back to the new account creation screen.
         */
        fun newAccountButtonClicked() {
            val directions =
                VerifyingEmailFragmentDirections.actionVerifyingEmailFragmentToUserNameFragment()
            navigateTo(directions)
        }
    }

    /**
     * Manages UI state transitions and updates.
     */
    private inner class StateHandler {

        /**
         * Called when entering the 'verifying' state.
         * Starts the countdown and updates UI.
         */
        fun tryingToVerify() {
            viewModel.startCounter()
            setResendButton(enabled = false)
            setTransitionToEnd()
            bindingEmail()
        }

        /**
         * Called when entering the 'waiting to resend' state.
         * Enables the resend button and updates the layout.
         */
        fun waitingResend() {
            setResendButton(enabled = true)
            setTransitionToStart()
            bindingEmail()
        }

        /**
         * Updates the countdown timer text in the UI.
         */
        fun updateCounterUiValue(counter: Int) {
            val value = if (counter >= 10) "$counter" else "0$counter"
            binding.fragVerifyingEmailTimer.text = value
        }


        // Binds the email address to the UI.
        private fun bindingEmail() {
            lifecycle.currentState.executeOnState(
                onViewCreating = { binding.fragVerifyingEmailSentTo.text = args.email }
            )
        }

        private fun setResendButton(enabled: Boolean) {
            binding.fragVerifyingEmailButtonResend.isEnabled = enabled
        }

        // Triggers layout transition to "verifying" state.
        private fun setTransitionToEnd() {
            binding.fragVerifyingEmailMain.run {
                lifecycle.currentState.executeOnState(
                    onViewResumed = { transitionToStart() },
                    onViewCreating = { jumpToState(R.id.frag_verifying_email_state_1) }
                )
            }
        }

        // Triggers layout transition to "resend available" state.
        private fun setTransitionToStart() {
            binding.fragVerifyingEmailMain.run {
                lifecycle.currentState.executeOnState(
                    onViewResumed = { transitionToEnd() },
                    onViewCreating = { jumpToState(R.id.frag_verifying_email_state_2) }
                )
            }
        }
    }

}





