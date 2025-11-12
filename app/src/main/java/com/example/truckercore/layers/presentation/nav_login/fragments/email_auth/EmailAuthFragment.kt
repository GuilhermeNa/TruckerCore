package com.example.truckercore.layers.presentation.nav_login.fragments.email_auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.widget.addTextChangedListener
import com.example.truckercore.core.my_lib.expressions.launchAndRepeatOnFragmentStartedLifeCycle
import com.example.truckercore.core.my_lib.expressions.navigateToDirection
import com.example.truckercore.core.my_lib.expressions.showWarningSnackbar
import com.example.truckercore.databinding.FragmentEmailAuthBinding
import com.example.truckercore.layers.presentation.base.abstractions._public.PublicLockedFragment
import com.example.truckercore.layers.presentation.common.LoadingDialog
import com.example.truckercore.layers.presentation.viewmodels.view_models.email_auth.EmailAuthViewModel
import com.example.truckercore.layers.presentation.viewmodels.view_models.email_auth.effect.EmailAuthEffect
import com.example.truckercore.layers.presentation.viewmodels.view_models.email_auth.event.EmailAuthenticationFragmentEvent
import com.example.truckercore.layers.presentation.viewmodels.view_models.email_auth.state.EmailAuthUiStatus
import com.example.truckercore.presentation.nav_login.fragments.email_auth.EmailAuthFragmentDirections
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

private val verifyEmailFragmentDirection =
    EmailAuthFragmentDirections.actionEmailAuthFragmentToVerifyingEmailFragment()

private val loginFragmentDirection =
    EmailAuthFragmentDirections.actionGlobalLoginFragment()

private typealias BackgroundClicked = EmailAuthenticationFragmentEvent.Click.Background
private typealias CreateButtonClicked = EmailAuthenticationFragmentEvent.Click.ButtonCreate
private typealias AlreadyRegisteredAccountButtonClicked = EmailAuthenticationFragmentEvent.Click.ButtonHaveAccount
private typealias ImeActionDoneClicked = EmailAuthenticationFragmentEvent.Click.ImeActionDone

private typealias TypeEmail = EmailAuthenticationFragmentEvent.Typing.Email
private typealias TypePassword = EmailAuthenticationFragmentEvent.Typing.Password
private typealias TypeConfirmation = EmailAuthenticationFragmentEvent.Typing.Confirmation


// navigateToNoConnection

class EmailAuthFragment : PublicLockedFragment() {

    // View Binding
    private var _binding: FragmentEmailAuthBinding? = null
    private val binding get() = _binding!!

    // ViewModel and Helpers
    private val viewModel: EmailAuthViewModel by viewModel()
    private val stateHandler = EmailAuthFragmentUiStateHandler()

    // Helper View
    private val dialog: LoadingDialog by lazy { LoadingDialog(requireContext()) }

    //----------------------------------------------------------------------------------------------
    // onCreate()
    //----------------------------------------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        launchAndRepeatOnFragmentStartedLifeCycle {
            setFragmentStateManager()
            setFragmentEffectManager()
        }
    }

    private fun CoroutineScope.setFragmentStateManager() {
        launch {
            viewModel.state.collect { state ->
                stateHandler.handleUiComponents(state.components)
                handleUiStatus(state.status)
            }
        }
    }

    private fun handleUiStatus(status: EmailAuthUiStatus) {
        if (status.isCreating()) dialog.show()
        else dialog.dismissIfShowing()
    }

    private suspend fun setFragmentEffectManager() {
        viewModel.effect.collect { effect ->
            when (effect) {
                is EmailAuthEffect.Navigation -> handleNavigationEffect(effect)
                is EmailAuthEffect.View -> handleViewEffect(effect)
            }
        }
    }

    private fun handleNavigationEffect(effect: EmailAuthEffect.Navigation) {
        when (effect) {
            EmailAuthEffect.Navigation.ToLogin -> navigateToDirection(loginFragmentDirection)

            EmailAuthEffect.Navigation.ToVerifyEmail ->
                navigateToDirection(verifyEmailFragmentDirection)

            EmailAuthEffect.Navigation.ToNotification -> navigateToErrorActivity(requireActivity())

            EmailAuthEffect.Navigation.ToNoConnection -> TODO()

        }
    }

    private fun handleViewEffect(effect: EmailAuthEffect.View) {
        when (effect) {
            EmailAuthEffect.View.ClearFocusAndKeyboard -> hideKeyboardAndClearFocus()
            is EmailAuthEffect.View.WarningToast -> showWarningSnackbar(effect.message)
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
        setImeOptionsClickListener()
        setEmailTextChangeListener()
        setPasswordTextChangeListener()
        setConfirmationTextChangeListener()
    }

    private fun setBackgroundClickListener() {
        binding.fragEmailAuthMain.setOnClickListener {
            viewModel.onEvent(BackgroundClicked)
        }
    }

    private fun setCreateButtonListener() = with(binding) {
        fragEmailAuthRegisterButton.setOnClickListener {
            viewModel.onEvent(CreateButtonClicked)
        }
    }

    private fun setAlreadyRegisteredButtonListener() {
        binding.fragEmailAuthAlreadyRegisteredButton.setOnClickListener {
            viewModel.onEvent(AlreadyRegisteredAccountButtonClicked)
        }
    }

    private fun setImeOptionsClickListener() {
        binding.fragEmailAuthConfirmPasswordEditText.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.onEvent(ImeActionDoneClicked)
                false
            } else false
        }
    }

    private fun setEmailTextChangeListener() {
        binding.fragEmailAuthEmailEditText.addTextChangedListener { editable ->
            onViewResumed {
                val text = editable.toString()
                viewModel.onEvent(TypeEmail(text))
            }
        }
    }

    private fun setPasswordTextChangeListener() {
        binding.fragEmailAuthPasswordEditText.addTextChangedListener { editable ->
            onViewResumed {
                val text = editable.toString()
                viewModel.onEvent(TypePassword(text))
            }
        }
    }

    private fun setConfirmationTextChangeListener() {
        binding.fragEmailAuthConfirmPasswordEditText.addTextChangedListener { editable ->
            onViewResumed {
                val text = editable.toString()
                viewModel.onEvent(TypeConfirmation(text))
            }
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
