package com.example.truckercore.view.nav_login.fragments.email_auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.example.truckercore._shared.expressions.hideKeyboardAndClearFocus
import com.example.truckercore._shared.expressions.logEffect
import com.example.truckercore._shared.expressions.logState
import com.example.truckercore._shared.expressions.showRedSnackBar
import com.example.truckercore.databinding.FragmentEmailAuthBinding
import com.example.truckercore.view._shared._base.fragments.CloseAppFragment
import com.example.truckercore.view._shared.expressions.launchOnFragment
import com.example.truckercore.view._shared.views.dialogs.LoadingDialog
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthViewModel
import com.example.truckercore.view_model.view_models.email_auth.effect.EmailAuthEffect
import com.example.truckercore.view_model.view_models.email_auth.event.EmailAuthEvent
import com.example.truckercore.view_model.view_models.email_auth.uiState.EmailAuthUiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * EmailAuthFragment is responsible for handling the email registration screen in the application.
 * It allows users to create an account using an email, password, and confirmation, and it reacts
 * to ViewModel state, effect, and event updates to properly manage UI feedback and navigation.
 */
class EmailAuthFragment : CloseAppFragment() {

    private var _binding: FragmentEmailAuthBinding? = null
    private val binding get() = _binding!!

    private val stateHandler by lazy { EmailAuthStateHandler() }

    private val navigator by lazy { EmailAuthNavigator(findNavController()) }

    private val dialog: LoadingDialog by lazy { LoadingDialog(requireContext()) }

    private val viewModel: EmailAuthViewModel by viewModel()

    //----------------------------------------------------------------------------------------------
    // onCreate()
    //----------------------------------------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        launchOnFragment {
            setFragmentStateManager()
            setFragmentEffectManager()
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
                stateHandler.handleUiComponents(state.components)
                handleUiStatus(state.status)
            }
        }
    }

    private fun handleUiStatus(status: EmailAuthUiStatus) {
        if (status.isCreating()) dialog.show()
        else dialog.dismissIfShowing()
    }

    /**
     * Collects and handles side effects emitted by the ViewModel, such as success or failure messages.
     */
    private suspend fun setFragmentEffectManager() {
        viewModel.effect.collect { effect ->
            logEffect(this@EmailAuthFragment, effect)
            when (effect) {
                EmailAuthEffect.ClearFocusAndHideKeyboard ->
                    hideKeyboardAndClearFocus()

                EmailAuthEffect.NavigateToLogin ->
                    navigator.navigateToLogin()

                EmailAuthEffect.NavigateToNotification ->
                    navigator.navigateToNotification(requireActivity())

                EmailAuthEffect.NavigateToVerifyEmail ->
                    navigator.navigateToVerifyEmail()

                is EmailAuthEffect.ShowToast ->
                    showRedSnackBar(effect.message)
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
        stateHandler.initialize(binding)
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
        setImeOptionsClickListener()
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

    private fun setImeOptionsClickListener() {
        binding.fragEmailAuthConfirmPasswordEditText.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                v.clearFocus()
                false
            } else false
        }
    }

    //----------------------------------------------------------------------------------------------
    // onDestroyView()
    //----------------------------------------------------------------------------------------------
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
