package com.example.truckercore.view.fragments.email_auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.example.truckercore.databinding.FragmentEmailAuthBinding
import com.example.truckercore.view.expressions.hideKeyboard
import com.example.truckercore.view.expressions.navigateTo
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthFragEvent.AlreadyHaveAccountButtonCLicked
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthFragEvent.CreateAccountButtonCLicked
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthFragState.Creating
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthFragState.EmailAuthFragError
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthFragState.EmailAuthFragSuccess
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthFragState.EmailAuthFragSuccess.UserCreatedAndEmailFailed
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthFragState.EmailAuthFragSuccess.UserCreatedAndEmailSent
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthFragState.Error
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthFragState.Success
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthFragState.WaitingInput
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthViewModel
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthVmArgs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * The EmailAuthFragment is responsible for handling the email authentication process.
 * It manages email validation, displays relevant UI states, and navigates to the next step in the authentication flow.
 * The fragment uses a ViewModel for business logic and an EmailAuthUiHandler to handle UI updates.
 */
class EmailAuthFragment : Fragment() {

    // Binding reference for the fragment's views
    private var _binding: FragmentEmailAuthBinding? = null
    private val binding get() = _binding!!

    // The EmailAuthUiHandler is responsible for managing UI-specific updates within this fragment
    private var _uiHandler: EmailAuthUiHandler? = null
    private val uiHandler get() = _uiHandler!!

    // Initializes the necessary arguments and ViewModel.
    private val vmArgs by lazy {
        val args: EmailAuthFragmentArgs by navArgs()
        EmailAuthVmArgs(name = args.name)
    }
    private val viewModel: EmailAuthViewModel by viewModel { parametersOf(vmArgs) }

    // onCreate() ----------------------------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                setFragmentStateManager()
                setFragmentEventsManager()
            }
        }
    }

    private fun CoroutineScope.setFragmentStateManager() {
        launch {
            viewModel.fragmentState.collect { state ->
                when (state) {
                    is WaitingInput -> Unit
                    is Creating -> handleCreatingState()
                    is Success -> handleSuccessState(state.type)
                    is Error -> handleErrorState(state.errorMap)
                }
            }
        }
    }

    private fun handleCreatingState() {
        uiHandler.setCreatingState()

        val email = binding.fragEmailAuthEmailEditText.text.toString()
        val password = binding.fragEmailAuthPasswordEditText.text.toString()
        val confirmation = binding.fragEmailAuthConfirmPasswordEditText.text.toString()

        viewModel.tryToAuthenticate(email, password, confirmation)
    }

    private fun handleSuccessState(type: EmailAuthFragSuccess) {
        uiHandler?.setSuccessState()
        navigateToVerificationFragment(type)
    }

    private fun navigateToVerificationFragment(type: EmailAuthFragSuccess) {
        val emailText = binding.fragEmailAuthEmailEditText.text.toString()

        with(EmailAuthFragmentDirections) {
            return@with when (type) {
                UserCreatedAndEmailSent ->
                    actionEmailAuthFragmentToVerifyingEmailFragment(
                        email = emailText,
                        emailSent = true
                    )

                UserCreatedAndEmailFailed ->
                    actionEmailAuthFragmentToVerifyingEmailFragment(
                        email = emailText,
                        emailSent = false
                    )
            }
        }.let { direction -> navigateTo(direction) }

        viewModel.setState(WaitingInput)
    }

    private fun handleErrorState(errorMap: HashMap<EmailAuthFragError, String>) {
        uiHandler.setErrorState(errorMap, lifecycle.currentState)
    }

    private suspend fun setFragmentEventsManager() {
        viewModel.event.collect { event ->
            when (event) {
                is CreateAccountButtonCLicked -> handleCreateButtonClicked()
                is AlreadyHaveAccountButtonCLicked -> navigateToLoginFragment()
            }
        }
    }

    private fun handleCreateButtonClicked() {
        hideKeyboardAndClearFocus()
        viewModel.setState(Creating)
    }

    private fun navigateToLoginFragment() {
        navigateTo(
            EmailAuthFragmentDirections.actionEmailAuthFragmentToLoginFragment()
        )
    }

    // onCreateView() ------------------------------------------------------------------------------
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEmailAuthBinding.inflate(layoutInflater)
        _uiHandler = EmailAuthUiHandler(binding)
        return binding.root
    }

    // onViewCreated -------------------------------------------------------------------------------
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRegisterButtonListener()
        setAlreadyRegisteredButtonListener()
        setMainLayoutClickListener()
        setPasswordEditTextFocusListener()
    }

    private fun setMainLayoutClickListener() {
        binding.fragEmailAuthMain.setOnClickListener {
            hideKeyboardAndClearFocus()
        }
    }

    private fun hideKeyboardAndClearFocus() {
        hideKeyboard()
        uiHandler?.clearLayoutFocus()
    }

    private fun setPasswordEditTextFocusListener() {
        binding.fragEmailAuthPasswordEditText.setOnFocusChangeListener { _, hasFocus ->
            //  uiHandler?.setPasswordHelper(selected = hasFocus)
        }
    }

    private fun setRegisterButtonListener() {
        binding.fragEmailAuthRegisterButton.setOnClickListener {
            viewModel.setEvent(CreateAccountButtonCLicked)
        }
    }

    private fun setAlreadyRegisteredButtonListener() {
        binding.fragEmailAuthAlreadyRegisteredButton.setOnClickListener {
            viewModel.setEvent(AlreadyHaveAccountButtonCLicked)
        }
    }

    // onViewDestroyed() ---------------------------------------------------------------------------
    override fun onDestroyView() {
        super.onDestroyView()
        _uiHandler = null
        _binding = null
    }

}