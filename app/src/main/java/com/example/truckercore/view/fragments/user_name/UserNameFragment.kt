package com.example.truckercore.view.fragments.user_name

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.truckercore.R
import com.example.truckercore.databinding.FragmentUserNameBinding
import com.example.truckercore.view.expressions.hideKeyboard
import com.example.truckercore.view.expressions.navigateTo
import com.example.truckercore.view.expressions.transitionOnLifecycle
import com.example.truckercore.view.fragments.base.CloseAppFragment
import com.example.truckercore.view_model.view_models.user_name.UserNameFragEvent.BackgroundClicked
import com.example.truckercore.view_model.view_models.user_name.UserNameFragEvent.FabCLicked
import com.example.truckercore.view_model.view_models.user_name.UserNameFragEvent.NavigateToEmailAuth
import com.example.truckercore.view_model.view_models.user_name.UserNameFragState.Error
import com.example.truckercore.view_model.view_models.user_name.UserNameFragState.UserNameFragErrorType
import com.example.truckercore.view_model.view_models.user_name.UserNameFragState.ValidName
import com.example.truckercore.view_model.view_models.user_name.UserNameFragState.WaitingForInput
import com.example.truckercore.view_model.view_models.user_name.UserNameViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * UserNameFragment is responsible for handling the user input for the username.
 * It manages the UI state using the ViewModel and updates the UI based on the user input.
 * The fragment validates the input, handles UI transitions, and navigates to the next screen if valid.
 * It uses MotionLayout for animations and state transitions.
 */
class UserNameFragment : CloseAppFragment() {

    // Binding reference to the fragment's views
    private var _binding: FragmentUserNameBinding? = null
    private val binding get() = _binding!!

    // The StateHandler is responsible for managing the UI state updates
    private var _uiHandler: UiHandler? = null
    private val uiHandler get() = _uiHandler!!

    // ViewModel responsible for business logic and state management of the fragment
    private val viewModel: UserNameViewModel by viewModel()

    // onCreate ------------------------------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                setFragmentStateManager()
                setFragmentEventManager()
            }
        }
    }

    /**
     * Manages the state changes from the ViewModel and updates the UI accordingly.
     * Responds to valid name and error states.
     */
    private fun CoroutineScope.setFragmentStateManager() {
        launch {
            viewModel.fragState.collect { state ->
                when (state) {
                    is WaitingForInput -> Unit
                    is ValidName -> handleValidName(state.name)
                    is Error -> handleError(state.type)
                }
            }
        }
    }

    /**
     * Handles the valid name state, navigating to the next screen and reset state.
     */
    private fun handleValidName(name: String) {
        // Reset to initial state
        viewModel.setState(WaitingForInput)

        // Update the editText with a formated name
        uiHandler.bindEditText(name)

        // Navigate to the email authentication screen
        viewModel.setEvent(NavigateToEmailAuth(args = name))
    }

    /**
     * Handles the error state by updating the UI with the corresponding error message.
     */
    private fun handleError(type: UserNameFragErrorType) {
        uiHandler.handleErrorState(type)
    }

    /**
     * Manages events collected from the ViewModel, such as FAB click, background click and navigation.
     */
    private suspend fun setFragmentEventManager() {
        viewModel.fragEvent.collect { event ->
            when (event) {
                is FabCLicked -> handleFabClicked()
                is BackgroundClicked -> handleBackgroundClicked()
                is NavigateToEmailAuth -> handleNavigateToEmail(event.args)
            }
        }
    }

    /**
     * Handle the navigation to Email Auth Fragment
     * @param args The name of the user define on EditText.
     */
    private fun handleNavigateToEmail(args: String) {
        val direction = UserNameFragmentDirections.actionUserNameFragmentToEmailAuthFragment(args)
        navigateTo(direction)
    }

    /**
     * Handles the background click event (e.g., clearing focus and hiding the keyboard).
     */
    private fun handleBackgroundClicked() {
        uiHandler.handleBackgroundClicked()
    }

    /**
     * Handles the FAB click event, validating the name entered by the user.
     */
    private fun handleFabClicked() {
        val name = binding.fragUserNameText.text.toString()
        viewModel.validateName(name)
    }

    // onCreateView --------------------------------------------------------------------------------
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserNameBinding.inflate(layoutInflater)
        _uiHandler = UiHandler(this)
        return binding.root
    }

    // onViewCreated -------------------------------------------------------------------------------
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBackgroundClickListener()
        setFabClickListener()
    }

    /**
     * Sets the listener for the FAB click event.
     */
    private fun setFabClickListener() {
        binding.fragUserNameFab.setOnClickListener {
            viewModel.setEvent(FabCLicked)
        }
    }

    /**
     * Sets the listener for the background click event.
     */
    private fun setBackgroundClickListener() {
        binding.fragUserNameMain.setOnClickListener {
            viewModel.setEvent(BackgroundClicked)
        }
    }

    // onDestroyView -------------------------------------------------------------------------------
    override fun onDestroyView() {
        super.onDestroyView()
        _uiHandler = null
        _binding = null
    }

    //----------------------------------------------------------------------------------------------
    // Helper classes Impl
    //----------------------------------------------------------------------------------------------
    /**
     * The UiHandler is responsible for updating the UI based on state changes.
     * It manages error handling, background click behavior, and transitions in the UI.
     */
    private class UiHandler(val fragment: UserNameFragment) {

        private val binding = fragment.binding
        private val lifecycleState get() = fragment.lifecycle.currentState

        /**
         * Handles the background click event by clearing the focus from the input field
         * and hiding the keyboard.
         */
        fun handleBackgroundClicked() {
            binding.fragUserNameText.clearFocus()
            fragment.hideKeyboard()
        }

        /**
         * Handles the error state by updating the error message in the UI and transitioning
         * to the error state in the MotionLayout.
         */
        fun handleErrorState(errorType: UserNameFragErrorType) {
            // Set the UI message
            binding.fragUserNameError.text = errorType.message

            // Change Ui State to error
            binding.fragUserNameMotion.transitionOnLifecycle(
                lifecycle = lifecycleState,
                viewResumed = { it.transitionToEnd() },
                viewRestoring = { it.jumpToState(R.id.frag_user_name_scene_state_end) }
            )
        }

        fun bindEditText(formatedName: String) {
            binding.fragUserNameText.setText(formatedName)
        }

    }

}