package com.example.truckercore.layers.presentation.login.view.fragments.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.truckercore.core.config.flavor.FlavorService
import com.example.truckercore.core.my_lib.classes.Email
import com.example.truckercore.core.my_lib.classes.EmailCredential
import com.example.truckercore.core.my_lib.classes.Password
import com.example.truckercore.core.my_lib.expressions.navigateToDirection
import com.example.truckercore.core.my_lib.expressions.showToast
import com.example.truckercore.databinding.FragmentLoginBinding
import com.example.truckercore.layers.presentation.base.abstractions.view._public.PublicLockedFragment
import com.example.truckercore.layers.presentation.common.LoadingDialog
import com.example.truckercore.layers.presentation.login.view_model.login.LoginViewModel
import com.example.truckercore.layers.presentation.login.view_model.login.helpers.LoginFragmentEffect
import com.example.truckercore.layers.presentation.login.view_model.login.helpers.LoginFragmentEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * LoginFragment
 *
 * This fragment is responsible for handling user login. It manages UI state updates,
 * user interactions, and navigation events triggered by the [LoginViewModel].
 */
class LoginFragment : PublicLockedFragment() {

    // View binding backing property
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    // Loading dialog displayed during login operations
    private var dialog: LoadingDialog? = null

    // ViewModel and dependencies injected via Koin
    private val viewModel: LoginViewModel by viewModel()
    private val flavorService: FlavorService by inject()

    // Handles rendering of UI state emitted by ViewModel
    private val stateHandler = LoginFragmentUiStateHandler()

    //----------------------------------------------------------------------------------------------
    // Lifecycle - onCreate
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
     * Collects stateFlow from the ViewModel and delegates UI updates to the state handler.
     */
    private fun CoroutineScope.setFragmentStateManager() {
        launch {
            viewModel.stateFlow.collect { state ->
                stateHandler.handle(state, dialog)
            }
        }
    }

    /**
     * Collects effectFlow from the ViewModel and performs navigation or one-off actions.
     */
    private suspend fun setFragmentEffectManager() {

        // Navigation directions
        val toForgetPassword = LoginFragmentDirections
            .actionLoginFragmentToForgetPasswordFragment()

        val toEmailAuth = LoginFragmentDirections.actionGlobalEmailAuthFragment()

        val toContinueRegister = LoginFragmentDirections.actionGlobalContinueRegisterFragment()

        viewModel.effectFlow.collect { effect ->
            when (effect) {
                LoginFragmentEffect.Navigation.ToCheckIn ->
                    flavorService.navigateToCheckIn(requireActivity())

                LoginFragmentEffect.Navigation.ToForgetPassword ->
                    navigateToDirection(toForgetPassword)

                LoginFragmentEffect.Navigation.ToNewUser ->
                    navigateToDirection(toEmailAuth)

                LoginFragmentEffect.Navigation.ToNoConnection ->
                    navigateToNoConnection(this, ::tryLogin)

                LoginFragmentEffect.Navigation.ToNotification ->
                    navigateToErrorActivity(requireActivity())

                is LoginFragmentEffect.ShowErrorToast ->
                    showToast(effect.message)

                is LoginFragmentEffect.Navigation.ToContinueRegister ->
                    navigateToDirection(toContinueRegister)

                else -> throw IllegalArgumentException("LoginFragment: Unknown navigation effect ($effect)")
            }
        }
    }

    /**
     * Attempts to log in using credentials extracted from user input.
     */
    private fun tryLogin() {
        val credential = getCredential()
        viewModel.tryLogin(credential)
    }

    /**
     * Reads email and password fields and returns a strongly typed EmailCredential.
     */
    private fun getCredential(): EmailCredential {
        val emailTxt = binding.fragLoginEmailText.text.toString()
        val passTxt = binding.fragLoginPasswordText.text.toString()

        return EmailCredential(
            email = Email.from(emailTxt),
            password = Password.from(passTxt)
        )
    }

    //----------------------------------------------------------------------------------------------
    // Lifecycle - onCreateView
    //----------------------------------------------------------------------------------------------
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        stateHandler.initialize(binding)
        return binding.root
    }

    //----------------------------------------------------------------------------------------------
    // Lifecycle - onViewCreated
    //----------------------------------------------------------------------------------------------
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog = LoadingDialog(requireContext())
        setCheckBoxClickListener()
        setEnterButtonClickListener()
        setNewAccountButtonClickListener()
        setForgetPasswordButtonClickListener()
        setEmailChangeListener()
        setPasswordChangeListener()
        setImeOptionsClickListener()
    }

    /**
     * Observes email text changes and dispatches an event to the ViewModel.
     */
    private fun setEmailChangeListener() {
        binding.fragLoginEmailText.addTextChangedListener { editable ->
            val text = editable.toString()
            val event = LoginFragmentEvent.TextChange.Email(text)
            viewModel.onEvent(event)
        }
    }

    /**
     * Handles checkbox toggling (e.g., remember me option).
     */
    private fun setCheckBoxClickListener() {
        binding.fragLoginCheckbox.setOnCheckedChangeListener { checkBox, _ ->
            val event = LoginFragmentEvent.Click.Checkbox(checkBox.isChecked)
            viewModel.onEvent(event)
        }
    }

    /**
     * Observes password text changes and informs the ViewModel.
     */
    private fun setPasswordChangeListener() {
        binding.fragLoginPasswordText.addTextChangedListener { editable ->
            val text = editable.toString()
            val event = LoginFragmentEvent.TextChange.Password(text)
            viewModel.onEvent(event)
        }
    }

    /**
     * Sets listener for the "Enter" (Login) button.
     */
    private fun setEnterButtonClickListener() {
        binding.fragLoginEnterButton.setOnClickListener {
            tryLogin()
        }
    }

    /**
     * Handles "Create New Account" button clicks.
     */
    private fun setNewAccountButtonClickListener() {
        binding.fragLoginNewAccountButton.setOnClickListener {
            viewModel.onEvent(LoginFragmentEvent.Click.NewAccount)
        }
    }

    /**
     * Handles "Forgot Password" button clicks.
     */
    private fun setForgetPasswordButtonClickListener() {
        binding.fragLoginForgetPasswordButton.setOnClickListener {
            viewModel.onEvent(LoginFragmentEvent.Click.ForgetPassword)
        }
    }

    private fun setImeOptionsClickListener() {
        binding.fragLoginPasswordText
            .setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    val credential = getCredential()
                    val newEvent = LoginFragmentEvent.Click.Enter(credential)
                    viewModel.onEvent(newEvent)
                    false
                } else false
            }
    }

    //----------------------------------------------------------------------------------------------
    // Lifecycle - onDestroyView
    //----------------------------------------------------------------------------------------------
    override fun onDestroyView() {
        super.onDestroyView()
        dialog?.dismiss()
        dialog = null
        _binding = null
    }

}