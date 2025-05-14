package com.example.truckercore.view.fragments.email_auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.truckercore.databinding.FragmentEmailAuthBinding
import com.example.truckercore.model.errors.AppExceptionOld
import com.example.truckercore.model.shared.utils.expressions.handleOnUi
import com.example.truckercore.view.activities.NotificationActivity
import com.example.truckercore._utils.expressions.clearFocusIfNeeded
import com.example.truckercore._utils.expressions.hideKeyboard
import com.example.truckercore._utils.expressions.showToast
import com.example.truckercore.view.fragments._base.CloseAppFragment
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthFragEffect
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthFragEvent.AlreadyHaveAccountButtonCLicked
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthFragEvent.CreateAccountButtonClicked
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthFragState.Creating
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthFragState.Success
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthFragState.UserInputError
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthFragState.WaitingInput
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthUserInputValidationResult
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
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
    private var _stateHandler: EmailAuthStateHandler? = null
    private val stateHandler get() = _stateHandler!!

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
                setFragmentEventsManager()
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
                when (state) {
                    is WaitingInput -> stateHandler.dismissDialog()
                    is Creating -> handleCreatingState()
                    is Success -> handleSuccessState()
                    is UserInputError -> handleUserInputErrorState(state.validationResult)
                }
            }
        }
    }

    /**
     * Updates the UI to reflect the account creation process.
     */
    private fun handleCreatingState() {
        stateHandler.setCreatingState()
    }

    /**
     * Handles successful account creation by updating the UI and navigating to the next screen.
     */
    private fun handleSuccessState() {
        /*        stateHandler.setSuccessState()
                val direction = EmailAuthFragmentDirections.actionEmailAuthFragmentToUserNameFragment()
                navigateTo(direction)*/
    }

    /**
     * Displays UI errors based on the received error map.
     */
    private fun handleUserInputErrorState(validationResult: EmailAuthUserInputValidationResult) {
        stateHandler.setUserInputErrorState(validationResult, lifecycle.currentState)
    }

    /**
     * Collects and handles side effects emitted by the ViewModel, such as success or failure messages.
     */
    private fun CoroutineScope.setFragmentEffectManager() {
        launch {
            viewModel.effect.collect { effect ->
                when (effect) {
                    is EmailAuthFragEffect.UserCreated -> handleUserCreatedEffect()
                    is EmailAuthFragEffect.UserCreationFailed ->
                        handleUserCreationFailedEffect(effect.error)
                }
            }
        }
    }

    /**
     * Triggers the success state once the user has been created.
     */
    private fun handleUserCreatedEffect() {
        viewModel.setState(Success)
    }

    /**
     * Handles failures during user creation and determines whether to show a toast or navigate to an error screen.
     */
    private fun handleUserCreationFailedEffect(error: AppExceptionOld) {
        viewModel.setState(WaitingInput)
        error.errorCode.let { ec ->
            ec.handleOnUi(
                onRecoverable = { message -> showToast(message) },
                onFatalError = { name, message ->
                    val intent = NotificationActivity.newInstance(
                        context = requireContext(),
                        title = name,
                        message = message
                    )
                    startActivity(intent)
                    requireActivity().finish()
                }
            )
        }
    }

    /**
     * Collects user interaction events and triggers corresponding UI and ViewModel logic.
     */
    private suspend fun setFragmentEventsManager() {
        viewModel.event.collect { event ->
            when (event) {
                is CreateAccountButtonClicked -> handleCreateButtonClicked()
                is AlreadyHaveAccountButtonCLicked -> handleAlreadyHaveAccountButtonClicked()
            }
        }
    }

    /**
     * Initiates account creation by collecting user input and passing it to the ViewModel.
     */
    private fun handleCreateButtonClicked() {
        hideKeyboardAndClearFocus()
        viewModel.setState(Creating)

        val email = binding.fragEmailAuthEmailEditText.text.toString()
        val password = binding.fragEmailAuthPasswordEditText.text.toString()
        val confirmation = binding.fragEmailAuthConfirmPasswordEditText.text.toString()
        viewModel.tryToAuthenticate(email, password, confirmation)
    }

    /**
     * Navigates to the login screen for users who already have an account.
     */
    private fun handleAlreadyHaveAccountButtonClicked() {
        /*     val direction = EmailAuthFragmentDirections.actionEmailAuthFragmentToLoginFragment()
             navigateTo(direction)*/
    }

    //----------------------------------------------------------------------------------------------
    // onCreateView()
    //----------------------------------------------------------------------------------------------
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEmailAuthBinding.inflate(layoutInflater)
        _stateHandler = EmailAuthStateHandler(this, binding)
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
    }

    /**
     * Hides the keyboard and clears focus from input fields when the main layout is tapped.
     */
    private fun setMainLayoutClickListener() {
        binding.fragEmailAuthMain.setOnClickListener {
            hideKeyboardAndClearFocus()
        }
    }

    /**
     * Utility to hide the keyboard and remove focus from all editable fields.
     */
    private fun hideKeyboardAndClearFocus() {
        hideKeyboard()
        listOf(
            binding.fragEmailAuthEmailLayout,
            binding.fragEmailAuthPasswordLayout,
            binding.fragEmailAuthConfirmPasswordLayout
        ).clearFocusIfNeeded()
    }

    /**
     * Sets the click listener for the "Register" button to trigger the account creation process.
     */
    private fun setRegisterButtonListener() {
        binding.fragEmailAuthRegisterButton.setOnClickListener {
            viewModel.setEvent(CreateAccountButtonClicked)
        }
    }

    /**
     * Sets the click listener for the "Already Registered" button to go to the login screen.
     */
    private fun setAlreadyRegisteredButtonListener() {
        binding.fragEmailAuthAlreadyRegisteredButton.setOnClickListener {
            viewModel.setEvent(AlreadyHaveAccountButtonCLicked)
        }
    }

    //----------------------------------------------------------------------------------------------
    // onDestroyView()
    //----------------------------------------------------------------------------------------------
    override fun onDestroyView() {
        super.onDestroyView()
        _stateHandler = null
        _binding = null
    }
}