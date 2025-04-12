package com.example.truckercore.view.fragments.verifying_email

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.example.truckercore.R
import com.example.truckercore.business_admin.view.activities.HomeActivity
import com.example.truckercore.databinding.FragmentVerifyingEmailBinding
import com.example.truckercore.view.activities.NotificationActivity
import com.example.truckercore.view.expressions.execute
import com.example.truckercore.view.expressions.navigateTo
import com.example.truckercore.view.expressions.showSnackBarGreen
import com.example.truckercore.view.expressions.showSnackBarRed
import com.example.truckercore.view.expressions.showToast
import com.example.truckercore.view.fragments.base.CloseAppFragment
import com.example.truckercore.view.sealeds.UiError
import com.example.truckercore.view_model.view_models.verifying_email.VerifyingEmailEffect.CounterReachZero
import com.example.truckercore.view_model.view_models.verifying_email.VerifyingEmailEffect.EmailVerificationFailed
import com.example.truckercore.view_model.view_models.verifying_email.VerifyingEmailEffect.EmailVerificationSucceed
import com.example.truckercore.view_model.view_models.verifying_email.VerifyingEmailEffect.SendEmailFailed
import com.example.truckercore.view_model.view_models.verifying_email.VerifyingEmailEffect.SendEmailSucceed
import com.example.truckercore.view_model.view_models.verifying_email.VerifyingEmailEvent.NewAccountButtonClicked
import com.example.truckercore.view_model.view_models.verifying_email.VerifyingEmailEvent.ResendButtonClicked
import com.example.truckercore.view_model.view_models.verifying_email.VerifyingEmailFragState
import com.example.truckercore.view_model.view_models.verifying_email.VerifyingEmailFragState.TryingToVerify
import com.example.truckercore.view_model.view_models.verifying_email.VerifyingEmailFragState.WaitingResend
import com.example.truckercore.view_model.view_models.verifying_email.VerifyingEmailViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class VerifyingEmailFragment : CloseAppFragment() {

    // Binding
    private var _binding: FragmentVerifyingEmailBinding? = null
    val binding get() = _binding!!

    // Fragment Handlers
    private val stateHandler by lazy { StateHandler() }
    private val eventHandler by lazy { EventHandler() }
    private val effectHandler by lazy { EffectHandler() }

    // Args
    val args: VerifyingEmailFragmentArgs by navArgs()

    // ViewModel
    private val viewModel: VerifyingEmailViewModel by viewModel()

    // onCreate ------------------------------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                setFragmentStateManager()
                setCounterStateManager()
                setEffectManager()
                setEventManager()
            }
        }
    }

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

    private fun CoroutineScope.setCounterStateManager() {
        launch {
            viewModel.counter.collect { counter ->
                stateHandler.updateCounterUiValue(counter)
                if (counter == 0) viewModel.setEffect(CounterReachZero)
            }
        }
    }

    private fun CoroutineScope.setEffectManager() {
        launch {
            viewModel.effect.collect { effect ->
                when (effect) {
                    is CounterReachZero -> effectHandler.counterReachZero()
                    is EmailVerificationSucceed -> effectHandler.emailVerificationSucceed()
                    is EmailVerificationFailed -> effectHandler.setEffectFailure(effect.uiError)
                    is SendEmailFailed -> effectHandler.setEffectFailure(effect.uiError)
                    is SendEmailSucceed -> effectHandler.sendEmailSucceed(effect.message)
                }
            }
        }
    }

    private suspend fun setEventManager() {
        viewModel.event.collect { event ->
            when (event) {
                ResendButtonClicked -> eventHandler.resendButtonClicked()
                NewAccountButtonClicked -> eventHandler.newAccountButtonClicked()
            }
        }
    }

    // onCreateView --------------------------------------------------------------------------------
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVerifyingEmailBinding.inflate(layoutInflater)
        return binding.root
    }

    // onViewCreated -------------------------------------------------------------------------------
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setResendButtonClickListener()
        setNewAccountButtonCLickListener()
    }

    private fun setResendButtonClickListener() {
        binding.fragVerifyingEmailButtonResend.setOnClickListener {
            viewModel.setEvent(ResendButtonClicked)
        }
    }

    private fun setNewAccountButtonCLickListener() {
        binding.fragVerifyingEmailButtonNewAccount.setOnClickListener {
            viewModel.setEvent(NewAccountButtonClicked)
        }
    }

    // onDestroyView -------------------------------------------------------------------------------
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //----------------------------------------------------------------------------------------------
    // Helper Classes
    //----------------------------------------------------------------------------------------------

    private inner class EffectHandler {

        fun counterReachZero() = viewModel.setState(WaitingResend)

        fun emailVerificationSucceed() {
            requireActivity().navigateTo(HomeActivity::class.java)
            requireActivity().finish()
        }

        fun setEffectFailure(uiError: UiError) {
            when (uiError) {
                is UiError.FatalError ->
                    navigateToNotificationActivity(uiError.title, uiError.message)

                is UiError.RecoverableError -> showSnackBarRed(uiError.message)
            }
        }

        private fun navigateToNotificationActivity(title: String, message: String) {
            val intent = NotificationActivity.newInstance(
                context = requireContext(),
                gifRes = R.drawable.gif_error,
                errorHeader = title,
                errorBody = message
            )
            startActivity(intent)
            requireActivity().finish()
        }

        fun sendEmailSucceed(message: String) {
            showToast(message)
            viewModel.setState(TryingToVerify)
        }

    }

    private inner class EventHandler {

        fun resendButtonClicked() {
            viewModel.sendVerificationEmail()
        }

        fun newAccountButtonClicked() {
            val directions =
                VerifyingEmailFragmentDirections.actionVerifyingEmailFragmentToUserNameFragment()
            navigateTo(directions)
        }

    }

    private inner class StateHandler {

        fun tryingToVerify() {
            viewModel.startCounter()
            setResendButton(enabled = false)
            setTransitionToEnd()
            bindingEmail()
        }

        fun waitingResend() {
            setResendButton(enabled = true)
            setTransitionToStart()
            bindingEmail()
        }

        fun updateCounterUiValue(counter: Int) {
            val value = if (counter >= 10) "$counter" else "0$counter"
            binding.fragVerifyingEmailTimer.text = value
        }

        private fun bindingEmail() {
            lifecycle.currentState.execute(
                onViewCreating = { binding.fragVerifyingEmailSentTo.text = args.email }
            )
        }

        private fun setResendButton(enabled: Boolean) {
            binding.fragVerifyingEmailButtonResend.isEnabled = enabled
        }

        private fun setTransitionToEnd() {
            binding.fragVerifyingEmailMain.run {
                lifecycle.currentState.execute(
                    onViewResumed = { transitionToStart() },
                    onViewCreating = { jumpToState(R.id.frag_verifying_email_state_1) }
                )
            }
        }

        private fun setTransitionToStart() {
            binding.fragVerifyingEmailMain.run {
                lifecycle.currentState.execute(
                    onViewResumed = { transitionToEnd() },
                    onViewCreating = { jumpToState(R.id.frag_verifying_email_state_2) }
                )
            }
        }


    }

}





