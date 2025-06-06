package com.example.truckercore.view.nav_login.fragments.user_name

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.truckercore._shared.expressions.hideKeyboardAndClearFocus
import com.example.truckercore._shared.expressions.navigateToActivity
import com.example.truckercore._shared.expressions.showRedSnackBar
import com.example.truckercore.databinding.FragmentUserNameBinding
import com.example.truckercore.view._shared.views.activities.NotificationActivity
import com.example.truckercore.view._shared.views.dialogs.LoadingDialog
import com.example.truckercore.view._shared._base.fragments.CloseAppFragment
import com.example.truckercore.view_model.view_models.user_name.UserNameEffect
import com.example.truckercore.view_model.view_models.user_name.UserNameEvent
import com.example.truckercore.view_model.view_models.user_name.UserNameUiState
import com.example.truckercore.view_model.view_models.user_name.UserNameViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserNameFragment : CloseAppFragment() {

    // Binding reference to the fragment's views
    private var _binding: FragmentUserNameBinding? = null
    val binding get() = _binding!!

    // The StateHandler is responsible for managing the UI state updates
    private var stateHandler: UserNameUiStateHandler? = null

    // ViewModel responsible for business logic and state management of the fragment
    private val viewModel: UserNameViewModel by viewModel()

    private val dialog by lazy { LoadingDialog(requireContext()) }

    // onCreate ------------------------------------------------------------------------------------
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
     * Manages the state changes from the ViewModel and updates the UI accordingly.
     * Responds to valid name and error states.
     */
    private fun CoroutineScope.setFragmentStateManager() {
        launch {
            viewModel.state.collect { state ->
                stateHandler?.handleUiComponents(state.fieldState, state.fabState)

                when (state.status) {
                    UserNameUiState.Status.AwaitingInput -> dialog.dismissIfShowing()

                    UserNameUiState.Status.CreatingSystemAccess -> dialog.show()

                    is UserNameUiState.Status.CriticalError -> {
                        dialog.dismissIfShowing()
                        val intent = NotificationActivity.newInstance(context = requireContext())
                        navigateToActivity(intent, true)
                    }

                    UserNameUiState.Status.Success -> dialog.dismissIfShowing()
                }
            }
        }
    }

    /**
     * Manages the effects from ViewModel interactions.
     */
    private suspend fun setFragmentEffectManager() {
        viewModel.effect.collect { effect ->
            when (effect) {
                is UserNameEffect.RecoverableError -> showRedSnackBar(effect.message)
            }
        }
    }

    // onCreateView --------------------------------------------------------------------------------
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserNameBinding.inflate(layoutInflater)
        stateHandler = UserNameUiStateHandler(binding.fragUserNameLayout, binding.fragUserNameFab)
        return binding.root
    }

    // onViewCreated -------------------------------------------------------------------------------
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBackgroundClickListener()
        setNameChangedListener()
        setFabClickListener()
    }

    private fun setNameChangedListener() {
        binding.fragUserNameText.addTextChangedListener { editable ->
            val text = editable.toString()
            viewModel.onEvent(UserNameEvent.UiEvent.TextChanged(text))
        }
    }

    private fun setBackgroundClickListener() {
        binding.fragUserNameMain.setOnClickListener {
            hideKeyboardAndClearFocus(binding.fragUserNameLayout)
        }
    }

    /**
     * Sets the listener for the FAB click event.
     */
    private fun setFabClickListener() {
        binding.fragUserNameFab.setOnClickListener {
            viewModel.onEvent(UserNameEvent.UiEvent.FabCLicked)
        }
    }

    // onDestroyView -------------------------------------------------------------------------------
    override fun onDestroyView() {
        super.onDestroyView()
        stateHandler = null
        _binding = null
    }

}