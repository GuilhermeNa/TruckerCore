package com.example.truckercore.view.nav_login.fragments.user_name

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.example.truckercore._shared.expressions.hideKeyboardAndClearFocus
import com.example.truckercore._shared.expressions.navigateToActivity
import com.example.truckercore._shared.expressions.showRedSnackBar
import com.example.truckercore.databinding.FragmentUserNameBinding
import com.example.truckercore.view._shared._base.fragments.CloseAppFragment
import com.example.truckercore.view._shared.expressions.launchOnFragment
import com.example.truckercore.view._shared.views.activities.NotificationActivity
import com.example.truckercore.view._shared.views.dialogs.LoadingDialog
import com.example.truckercore.view_model.view_models.user_name.effect.UserNameEffect
import com.example.truckercore.view_model.view_models.user_name.UserNameEvent
import com.example.truckercore.view_model.view_models.user_name.state.UserNameState
import com.example.truckercore.view_model.view_models.user_name.UserNameViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserNameFragment : CloseAppFragment() {

    private var _binding: FragmentUserNameBinding? = null
    val binding get() = _binding!!

    private val stateHandler: UserNameStateHandler by lazy { UserNameStateHandler() }

    private val dialog by lazy { LoadingDialog(requireContext()) }

    private val viewModel: UserNameViewModel by viewModel()

    // onCreate ------------------------------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        launchOnFragment {
            setStateManager()
            setEffectManager()
        }
    }

    /**
     * Manages the state changes from the ViewModel and updates the UI accordingly.
     * Responds to valid name and error states.
     */
    private fun CoroutineScope.setStateManager() {
        launch {
            viewModel.state.collect { state ->
                stateHandler?.handleUiComponents(state.fieldState, state.fabState)

                when (state.status) {
                    UserNameState.Status.AwaitingInput -> dialog.dismissIfShowing()

                    UserNameState.Status.CreatingSystemAccess -> dialog.show()

                    is UserNameState.Status.CriticalError -> {
                        dialog.dismissIfShowing()
                        val intent = NotificationActivity.newInstance(context = requireContext())
                        navigateToActivity(intent, true)
                    }

                    UserNameState.Status.Success -> dialog.dismissIfShowing()
                }
            }
        }
    }

    /**
     * Manages the effects from ViewModel interactions.
     */
    private suspend fun setEffectManager() {
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
        stateHandler = UserNameStateHandler(binding.fragUserNameLayout, binding.fragUserNameFab)
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