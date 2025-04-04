package com.example.truckercore.view.fragments.email_auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.truckercore.databinding.FragmentEmailAuthBinding
import com.example.truckercore.view.expressions.getBackPressCallback
import com.example.truckercore.view.expressions.hideKeyboard
import com.example.truckercore.view.expressions.navigateTo
import com.example.truckercore.view.helpers.ExitAppManager
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthFragEvent.AlreadyHaveAccountButtonCLicked
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthFragEvent.CreateAccountButtonCLicked
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthFragState.Creating
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthFragState.EmailAuthFragError
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthFragState.EmailAuthFragSuccess
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthFragState.EmailAuthFragSuccess.UserCreatedAndEmailFailed
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthFragState.EmailAuthFragSuccess.UserCreatedAndEmailSent
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthFragState.Error
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthFragState.Initial
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthFragState.Success
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class EmailAuthFragment : Fragment() {

    // Binding -------------------------------------------------------------------------------------
    private var _binding: FragmentEmailAuthBinding? = null
    private val binding get() = _binding!!
    private var stateHandler: EmailAuthStateHandler? = null

    // ViewModel -----------------------------------------------------------------------------------
    private val viewModel: EmailAuthViewModel by viewModel()

    // BackPressed ---------------------------------------------------------------------------------
    private val exitManager by lazy { ExitAppManager() }
    private val backPressCallback by lazy { getBackPressCallback(exitManager) }

    //----------------------------------------------------------------------------------------------
    // onCreate()
    //----------------------------------------------------------------------------------------------
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
                    is Initial -> Unit
                    is Creating -> handleCreatingState()
                    is Success -> handleSuccessState(state.type)
                    is Error -> handleErrorState(state.errorMap)
                }
            }
        }
    }

    private fun handleCreatingState() {
        stateHandler?.setCreatingState()

        val email = binding.fragEmailAuthEmailEditText.text.toString()
        val password = binding.fragEmailAuthPasswordEditText.text.toString()
        val confirmation = binding.fragEmailAuthConfirmPasswordEditText.text.toString()

        viewModel.tryToAuthenticate(email, password, confirmation)
    }

    private fun handleSuccessState(type: EmailAuthFragSuccess) {
        stateHandler?.setSuccessState()
        navigateToVerificationFragment(type)
    }

    private fun navigateToVerificationFragment(type: EmailAuthFragSuccess) {
        fun navigateToVerifyFragment(emailSent: Boolean) {
            val emailText = binding.fragEmailAuthEmailEditText.text.toString()
            navigateTo(
                EmailAuthFragmentDirections.actionEmailAuthFragmentToVerifyingEmailFragment(
                    email = emailText,
                    emailSent = emailSent
                )
            )
        }

        when (type) {
            UserCreatedAndEmailSent -> navigateToVerifyFragment(true)
            UserCreatedAndEmailFailed -> navigateToVerifyFragment(false)
        }

        viewModel.setState(Initial)
    }

    private fun handleErrorState(errorMap: HashMap<EmailAuthFragError, String>) {
        stateHandler?.setErrorState(errorMap, lifecycle.currentState)
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

    //----------------------------------------------------------------------------------------------
    // onCreateView()
    //----------------------------------------------------------------------------------------------
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEmailAuthBinding.inflate(layoutInflater)
        stateHandler = EmailAuthStateHandler(binding)
        return binding.root
    }

    //----------------------------------------------------------------------------------------------
    // onViewCreated()
    //----------------------------------------------------------------------------------------------
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRegisterButtonListener()
        setAlreadyRegisteredButtonListener()
        setMainLayoutClickListener()
        setPasswordEditTextFocusListener()
        setBackPressedCallback()
    }

    private fun setMainLayoutClickListener() {
        binding.fragEmailAuthMain.setOnClickListener {
            hideKeyboardAndClearFocus()
        }
    }

    private fun hideKeyboardAndClearFocus() {
        hideKeyboard()
        stateHandler?.clearLayoutFocus()
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

    private fun setBackPressedCallback() {
        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, backPressCallback)
    }

    //----------------------------------------------------------------------------------------------
    // onViewDestroyed()
    //----------------------------------------------------------------------------------------------
    override fun onDestroyView() {
        super.onDestroyView()
        exitManager.cancelCoroutines()
        _binding = null
    }

}