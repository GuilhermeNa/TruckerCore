package com.example.truckercore.layers.presentation.login.view.fragments.email_auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.truckercore.core.my_lib.expressions.applySystemBarsInsets
import com.example.truckercore.core.my_lib.expressions.navigateToDirection
import com.example.truckercore.core.my_lib.expressions.showWarningSnackbar
import com.example.truckercore.databinding.FragmentEmailAuthBinding
import com.example.truckercore.layers.presentation.base.abstractions.view.public.PublicLockedFragment
import com.example.truckercore.layers.presentation.common.LoadingDialog
import com.example.truckercore.layers.presentation.login.view_model.email_auth.EmailAuthViewModel
import com.example.truckercore.layers.presentation.login.view_model.email_auth.helpers.EmailAuthenticationFragmentEffect
import com.example.truckercore.layers.presentation.login.view_model.email_auth.helpers.EmailAuthenticationFragmentEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

private typealias CreateButtonClickedEvent = EmailAuthenticationFragmentEvent.Click.ButtonCreate
private typealias AlreadyRegisteredButtonClickedEvent = EmailAuthenticationFragmentEvent.Click.ButtonHaveAccount
private typealias ImeActionDoneClickedEvent = EmailAuthenticationFragmentEvent.Click.ImeActionDone

private typealias TypeEmailEvent = EmailAuthenticationFragmentEvent.Typing.Email
private typealias TypePasswordEvent = EmailAuthenticationFragmentEvent.Typing.Password
private typealias TypeConfirmationEvent = EmailAuthenticationFragmentEvent.Typing.Confirmation

private typealias RetryAuthenticationEvent = EmailAuthenticationFragmentEvent.RetryAuthentication

/**
 * Fragment responsible for handling the "Create Account with Email" flow.
 *
 * This screen allows the user to register a new account by providing:
 * - An email address
 * - A password
 * - A confirmation password
 *
 * The UI consists of:
 * - Three EditText fields (email, password, password confirmation)
 * - A button to attempt account creation
 * - A secondary button for users who already have an account (navigates to Login)
 *
 * While the authentication request is being processed,
 * a [LoadingDialog] is displayed with a loading animation.
 */
class EmailAuthFragment : PublicLockedFragment() {

    // View Binding
    private var _binding: FragmentEmailAuthBinding? = null
    private val binding get() = _binding!!

    // ViewModel & Helpers
    private val viewModel: EmailAuthViewModel by viewModel()
    private val stateHandler = EmailAuthFragmentUiStateHandler()

    // Navigation Directions
    private val verifyEmailFragmentDirection =
        EmailAuthFragmentDirections.actionEmailAuthFragmentToVerifyingEmailFragment()
    private val loginFragmentDirection =
        EmailAuthFragmentDirections.actionGlobalLoginFragment()

    /**
     * Dialog displayed during authentication attempts.
     */
    private var dialog: LoadingDialog? = null

    //----------------------------------------------------------------------------------------------
    // Lifecycle - onCreate()
    //----------------------------------------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                setFragmentStateManager()
                setFragmentEffectManager()
            }
        }
    }

    /**
     * Observes the [EmailAuthViewModel.stateFlow] and delegates UI
     * updates to the [stateHandler], such as error rendering, button states,
     * and dialog visibility.
     */
    private fun CoroutineScope.setFragmentStateManager() {
        launch {
            viewModel.stateFlow.collect { state ->
                stateHandler.handleState(dialog, state)
            }
        }
    }

    /**
     * Observes one-time effects emitted by the ViewModel,
     * including navigation, warnings, and retry flows.
     */
    private suspend fun setFragmentEffectManager() {
        viewModel.effectFlow.collect { effect ->
            when (effect) {

                is EmailAuthenticationFragmentEffect.Navigation ->
                    handleNavigationEffect(effect)

                is EmailAuthenticationFragmentEffect.WarningToast ->
                    showWarningSnackbar(effect.message)

                else -> Unit
            }
        }
    }

    /**
     * Redirects navigation events to the appropriate destination.
     * Also supports handling network failure through a retry callback.
     */
    private fun handleNavigationEffect(effect: EmailAuthenticationFragmentEffect.Navigation) {
        when (effect) {
            EmailAuthenticationFragmentEffect.Navigation.ToLogin ->
                navigateToDirection(loginFragmentDirection)

            EmailAuthenticationFragmentEffect.Navigation.ToVerifyEmail ->
                navigateToDirection(verifyEmailFragmentDirection)

            EmailAuthenticationFragmentEffect.Navigation.ToNotification ->
                navigateToErrorActivity(requireActivity())

            EmailAuthenticationFragmentEffect.Navigation.ToNoConnection -> {
                navigateToNoConnection(this) {
                    viewModel.onEvent(RetryAuthenticationEvent)
                }
            }
        }
    }

    //----------------------------------------------------------------------------------------------
    // Lifecycle - onCreateView()
    //----------------------------------------------------------------------------------------------
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEmailAuthBinding.inflate(layoutInflater)
        stateHandler.initialize(binding)
        return binding.root
    }

    //----------------------------------------------------------------------------------------------
    // Lifecycle - onViewCreated()
    //----------------------------------------------------------------------------------------------
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.applySystemBarsInsets()
        dialog = LoadingDialog(requireContext())
        setCreateButtonListener()
        setAlreadyRegisteredButtonListener()
        setImeOptionsClickListener()
        setEmailTextChangeListener()
        setPasswordTextChangeListener()
        setConfirmationTextChangeListener()
    }

    private fun setCreateButtonListener() = with(binding) {
        fragEmailAuthRegisterButton.setOnClickListener {
            viewModel.onEvent(CreateButtonClickedEvent)
        }
    }

    private fun setAlreadyRegisteredButtonListener() {
        binding.fragEmailAuthAlreadyRegisteredButton.setOnClickListener {
            viewModel.onEvent(AlreadyRegisteredButtonClickedEvent)
        }
    }

    private fun setImeOptionsClickListener() {
        binding.fragEmailAuthConfirmPasswordEditText
            .setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    viewModel.onEvent(ImeActionDoneClickedEvent)
                    false
                } else false
            }
    }

    private fun setEmailTextChangeListener() {
        binding.fragEmailAuthEmailEditText.addTextChangedListener { editable ->
            onViewResumed {
                viewModel.onEvent(TypeEmailEvent(editable.toString()))
            }
        }
    }

    private fun setPasswordTextChangeListener() {
        binding.fragEmailAuthPasswordEditText.addTextChangedListener { editable ->
            onViewResumed {
                viewModel.onEvent(TypePasswordEvent(editable.toString()))
            }
        }
    }

    private fun setConfirmationTextChangeListener() {
        binding.fragEmailAuthConfirmPasswordEditText.addTextChangedListener { editable ->
            onViewResumed {
                viewModel.onEvent(TypeConfirmationEvent(editable.toString()))
            }
        }
    }

    //----------------------------------------------------------------------------------------------
    // Lifecycle - onDestroyView()
    //----------------------------------------------------------------------------------------------
    override fun onDestroyView() {
        super.onDestroyView()
        dialog?.dismiss()
        dialog = null
        _binding = null
    }

}