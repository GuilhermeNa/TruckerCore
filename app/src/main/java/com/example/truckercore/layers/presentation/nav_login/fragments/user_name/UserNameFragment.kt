package com.example.truckercore.layers.presentation.nav_login.fragments.user_name

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.example.truckercore.core.expressions.hideKeyboardAndClearFocus
import com.example.truckercore.core.expressions.logEffect
import com.example.truckercore.core.expressions.logState
import com.example.truckercore.core.expressions.navigateToDirection
import com.example.truckercore.core.expressions.showRedSnackBar
import com.example.truckercore.databinding.FragmentUserNameBinding
import com.example.truckercore.presentation._shared._base.fragments.CloseAppFragment
import com.example.truckercore.presentation._shared.expressions.launchOnFragmentLifecycle
import com.example.truckercore.presentation._shared.expressions.navigateToNotification
import com.example.truckercore.presentation._shared.helpers.ViewBinder
import com.example.truckercore.presentation._shared.views.dialogs.LoadingDialog
import com.example.truckercore.domain.view_models.user_name.UserNameViewModel
import com.example.truckercore.domain.view_models.user_name.effect.UserNameEffect
import com.example.truckercore.domain.view_models.user_name.event.UserNameEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserNameFragment : CloseAppFragment() {

    private var _binding: FragmentUserNameBinding? = null
    val binding get() = _binding!!

    private val flavorService: com.example.truckercore.core.config.flavor.FlavorService by inject()

    private val dialog by lazy { LoadingDialog(requireContext()) }

    private val viewModel: UserNameViewModel by viewModel()

    // onCreate ------------------------------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initialize()
        launchOnFragmentLifecycle {
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
            viewModel.stateFlow.collect { state ->
                logState(this@UserNameFragment, state)
                handleComponents(state.components)
                handleStatus(state.status)
            }
        }
    }

    private fun handleStatus(status: com.example.truckercore.presentation.viewmodels.view_models.user_name.state.UserNameStatus) {
        if (status.isCreating()) dialog.show()
        else dialog.dismissIfShowing()
    }

    private fun handleComponents(components: com.example.truckercore.presentation.viewmodels.view_models.user_name.state.UserNameComponents) {
        ViewBinder.bindTextInput(components.nameComponent, binding.fragUserNameLayout)
        ViewBinder.bindFab(components.fabComponent, binding.fragUserNameFab)
    }

    /**
     * Manages the effects from ViewModel interactions.
     */
    private suspend fun setEffectManager() {
        viewModel.effectFlow.collect { effect ->
            logEffect(this@UserNameFragment, effect)
            when (effect) {
                UserNameEffect.Navigation.ToLogin -> {
                    val direction = UserNameFragmentDirections.actionGlobalLoginFragment()
                    navigateToDirection(direction)
                }

                UserNameEffect.Navigation.ToMain -> {
                    flavorService.navigateToMain(requireActivity())
                }

                UserNameEffect.Navigation.ToNotification -> {
                    requireActivity().navigateToNotification()
                }

                is UserNameEffect.ShowMessage -> {
                    showRedSnackBar(effect.message)
                }
            }
        }
    }

    // onCreateView --------------------------------------------------------------------------------
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserNameBinding.inflate(layoutInflater)
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
        _binding = null
    }

}