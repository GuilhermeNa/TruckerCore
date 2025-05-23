package com.example.truckercore.view.fragments.email_auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.truckercore.R
import com.example.truckercore._utils.expressions.doIfResumedOrElse
import com.example.truckercore._utils.expressions.hideKeyboardAndClearFocus
import com.example.truckercore._utils.expressions.logEffect
import com.example.truckercore._utils.expressions.logState
import com.example.truckercore._utils.expressions.navigateToDirection
import com.example.truckercore._utils.expressions.showRedSnackBar
import com.example.truckercore.databinding.FragmentEmailAuthBinding
import com.example.truckercore.view.dialogs.LoadingDialog
import com.example.truckercore.view.fragments._base.CloseAppFragment
import com.example.truckercore.view_model.view_models.email_auth.effect.EmailAuthEffect
import com.example.truckercore.view_model.view_models.email_auth.event.EmailAuthEvent
import com.example.truckercore.view_model.view_models.email_auth.uiState.EmailAuthUiState
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * EmailAuthFragment is responsible for handling the email registration screen in the application.
 * It allows users to create an account using an email, password, and confirmation, and it reacts
 * to ViewModel state, effect, and event updates to properly manage UI feedback and navigation.
 */
class EmailAuthFragment : CloseAppFragment() {

    // Binding reference for the fragment's views
    private var _binding: FragmentEmailAuthBinding? = null
    private val binding get() = _binding!!

    // Handles UI-specific logic and updates for this fragment
    private var stateHandler: EmailAuthUiStateHandler? = null

    private val dialog: LoadingDialog by lazy { LoadingDialog(requireContext()) }

    // ViewModel that contains state, effects, and events related to authentication logic
    private val viewModel: EmailAuthViewModel by viewModel()

    //----------------------------------------------------------------------------------------------
    // onCreate()
    //----------------------------------------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Observes ViewModel's state, effects, and events while lifecycle is in STARTED state
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                setFragmentStateManager()
                setFragmentEffectManager()
            }
        }
    }

    /**
     * Collects and handles state updates from the ViewModel.
     * It manages UI behavior based on the current authentication state.
     */
    private fun CoroutineScope.setFragmentStateManager() {
        launch {
            viewModel.state.collect { state ->
                logState(this@EmailAuthFragment, state)

                handleUiStatus(state.status)
                stateHandler?.handleEmailField(state.emailField)
                stateHandler?.handlePasswordField(state.passwordField)
                stateHandler?.handleConfirmationField(state.confirmationField)
                stateHandler?.handleCreateButton(state.createButton)
            }
        }
    }

    private fun handleUiStatus(uiStatus: EmailAuthUiState.Status) {
        when (uiStatus) {
            EmailAuthUiState.Status.AwaitingInput.Default -> {
                val stateId = R.id.initial
                doIfResumedOrElse(
                    resumed = { stateHandler?.transitionToState(stateId) },
                    orElse = { stateHandler?.jumpToState(stateId) }
                )
            }

            EmailAuthUiState.Status.AwaitingInput.Ready -> {
                val stateId = R.id.initial
                doIfResumedOrElse(
                    resumed = { stateHandler?.transitionToState(stateId) },
                    orElse = { stateHandler?.jumpToState(stateId) }
                )
            }

            EmailAuthUiState.Status.AwaitingInput.InputError.Status1 -> {
                val stateId = R.id.frag_email_auth_state_1
                doIfResumedOrElse(
                    resumed = { stateHandler?.transitionToState(stateId) },
                    orElse = { stateHandler?.jumpToState(stateId) }
                )
            }

            EmailAuthUiState.Status.AwaitingInput.InputError.Status2 -> {
                val stateId = R.id.frag_email_auth_state_2
                doIfResumedOrElse(
                    resumed = { stateHandler?.transitionToState(stateId) },
                    orElse = { stateHandler?.jumpToState(stateId) }
                )
            }

            EmailAuthUiState.Status.AwaitingInput.InputError.Status3 -> {
                val stateId = R.id.frag_email_auth_state_3
                doIfResumedOrElse(
                    resumed = { stateHandler?.transitionToState(stateId) },
                    orElse = { stateHandler?.jumpToState(stateId) }
                )
            }

            EmailAuthUiState.Status.AwaitingInput.InputError.Status4 -> {
                val stateId = R.id.frag_email_auth_state_4
                doIfResumedOrElse(
                    resumed = { stateHandler?.transitionToState(stateId) },
                    orElse = { stateHandler?.jumpToState(stateId) }
                )
            }

            EmailAuthUiState.Status.AwaitingInput.InputError.Status5 -> {
                val stateId = R.id.frag_email_auth_state_5
                doIfResumedOrElse(
                    resumed = { stateHandler?.transitionToState(stateId) },
                    orElse = { stateHandler?.jumpToState(stateId) }
                )
            }

            EmailAuthUiState.Status.AwaitingInput.InputError.Status6 -> {
                val stateId = R.id.frag_email_auth_state_6
                doIfResumedOrElse(
                    resumed = { stateHandler?.transitionToState(stateId) },
                    orElse = { stateHandler?.jumpToState(stateId) }
                )
            }

            EmailAuthUiState.Status.AwaitingInput.InputError.Status7 -> {
                val stateId = R.id.frag_email_auth_state_7
                doIfResumedOrElse(
                    resumed = { stateHandler?.transitionToState(stateId) },
                    orElse = { stateHandler?.jumpToState(stateId) }
                )
            }


            EmailAuthUiState.Status.Creating -> TODO()
            is EmailAuthUiState.Status.Error -> TODO()
            EmailAuthUiState.Status.Success -> TODO()
        }
    }

    /**
     * Collects and handles side effects emitted by the ViewModel, such as success or failure messages.
     */
    private fun CoroutineScope.setFragmentEffectManager() {
        launch {
            viewModel.effect.collect { effect ->
                logEffect(this@EmailAuthFragment, effect)

                when (effect) {
                    EmailAuthEffect.ClearFocusAndHideKeyboard -> {
                        hideKeyboardAndClearFocus()
                    }

                    EmailAuthEffect.NavigateToLogin -> {
                        val direction = EmailAuthFragmentDirections.actionGlobalLoginFragment()
                        navigateToDirection(direction)
                    }

                    is EmailAuthEffect.ShowErrorMessage -> {
                        showRedSnackBar(effect.message)
                    }
                }
            }
        }
    }

    private fun hideKeyboardAndClearFocus() {
        hideKeyboardAndClearFocus(
            binding.fragEmailAuthEmailLayout,
            binding.fragEmailAuthPasswordLayout,
            binding.fragEmailAuthConfirmPasswordLayout
        )
    }

    //----------------------------------------------------------------------------------------------
    // onCreateView()
    //----------------------------------------------------------------------------------------------
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEmailAuthBinding.inflate(layoutInflater)
        stateHandler = EmailAuthUiStateHandler(
            motionLayout = binding.fragEmailAuthMain,
            emailError = binding.fragEmailAuthEmailError,
            passwordError = binding.fragEmailAuthPasswordError,
            confirmationError = binding.fragEmailAuthConfirmationError,
            button = binding.fragEmailAuthRegisterButton
        )
        return binding.root
    }

    //----------------------------------------------------------------------------------------------
    // onViewCreated()
    //----------------------------------------------------------------------------------------------
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBackgroundClickListener()
        setCreateButtonListener()
        setAlreadyRegisteredButtonListener()
        setEmailTextChangeListener()
        setPasswordTextChangeListener()
        setConfirmationTextChangeListener()
    }

    // Notifica ao viewmodel que houve um evento de toque no background
    private fun setBackgroundClickListener() {
        binding.fragEmailAuthMain.setOnClickListener {
                viewModel.onEvent(EmailAuthEvent.UiEvent.Click.Background)
        }
    }

    // Notifica ao viewModel um evento de click no botao "Criar conta".
    private fun setCreateButtonListener() = with(binding) {
        fragEmailAuthRegisterButton.setOnClickListener {
            val clickEvent = EmailAuthEvent.UiEvent.Click.ButtonCreate
            viewModel.onEvent(clickEvent)
        }
    }

    // Notifica ao viewModel um evento de click no botao "Já tenho conta".
    private fun setAlreadyRegisteredButtonListener() {
        binding.fragEmailAuthAlreadyRegisteredButton.setOnClickListener {
            viewModel.onEvent(EmailAuthEvent.UiEvent.Click.ButtonAlreadyHaveAccount)
        }
    }

    private fun setEmailTextChangeListener() {
        binding.fragEmailAuthEmailEditText.addTextChangedListener { editable ->
            val text = editable.toString()
            viewModel.onEvent(EmailAuthEvent.UiEvent.Typing.EmailTextChange(text))
        }
    }

    private fun setPasswordTextChangeListener() {
        binding.fragEmailAuthPasswordEditText.addTextChangedListener { editable ->
            val text = editable.toString()
            viewModel.onEvent(EmailAuthEvent.UiEvent.Typing.PasswordTextChange(text))
        }
    }

    private fun setConfirmationTextChangeListener() {
        binding.fragEmailAuthConfirmPasswordEditText.addTextChangedListener { editable ->
            val text = editable.toString()
            viewModel.onEvent(EmailAuthEvent.UiEvent.Typing.ConfirmationTextChange(text))
        }
    }

    //----------------------------------------------------------------------------------------------
    // onDestroyView()
    //----------------------------------------------------------------------------------------------
    override fun onDestroyView() {
        super.onDestroyView()
        stateHandler = null
        _binding = null
    }

}
