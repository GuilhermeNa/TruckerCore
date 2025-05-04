package com.example.truckercore.view.fragments.user_name

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.truckercore.databinding.FragmentUserNameBinding
import com.example.truckercore.view.fragments._base.CloseAppFragment
import com.example.truckercore.view_model.view_models.user_name.UserNameFragEffect
import com.example.truckercore.view_model.view_models.user_name.UserNameFragEvent.BackgroundClicked
import com.example.truckercore.view_model.view_models.user_name.UserNameFragEvent.FabCLicked
import com.example.truckercore.view_model.view_models.user_name.UserNameFragState
import com.example.truckercore.view_model.view_models.user_name.UserNameFragState.Initial
import com.example.truckercore.view_model.view_models.user_name.UserNameFragState.UserInputError
import com.example.truckercore.view_model.view_models.user_name.UserNameViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserNameFragment : CloseAppFragment() {

    // Binding reference to the fragment's views
    private var _binding: FragmentUserNameBinding? = null
    val binding get() = _binding!!

    // The StateHandler is responsible for managing the UI state updates
    private var _stateHandler: UserNameFragStateHandler? = null
    private val stateHandler get() = _stateHandler!!

    // The EffectHandler handles effects triggered by the ViewModel
    private var _effectHandler: UserNameFragEffectHandler? = null
    private val effectHandler get() = _effectHandler!!

    // The EventHandler handles user events and interactions
    private var _eventHandler: UserNameFragEventFragHandler? = null
    private val eventHandler get() = _eventHandler!!

    // ViewModel responsible for business logic and state management of the fragment
    private val viewModel: UserNameViewModel by viewModel()

    // onCreate ------------------------------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                setFragmentStateManager()
                setFragmentEffectManager()
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
            viewModel.state.collect { state ->
                when (state) {
                    is Initial -> {
                        stateHandler.handleInitialState()
                    }

                    is UserNameFragState.Updating -> {
                        stateHandler.handleUpdatingState()
                    }

                    is UserInputError -> {
                        stateHandler.handleUserInputErrorState(state.text, lifecycle.currentState)
                    }
                }
            }
        }
    }

    /**
     * Manages the effects from ViewModel interactions.
     */
    private fun CoroutineScope.setFragmentEffectManager() {
        launch {
            viewModel.effect.collect { effect ->
                when (effect) {
                    is UserNameFragEffect.ProfileUpdated -> {
                        effectHandler.handleProfileUpdatedEffect()
                    }

                    is UserNameFragEffect.ProfileUpdateFailed -> {
                        effectHandler.handleProfileUpdateFailedEffect(effect.error)
                    }
                }
            }
        }
    }

    /**
     * Manages events collected from the ViewModel, such as FAB click, background click and navigation.
     */
    private suspend fun setFragmentEventManager() {
        viewModel.event.collect { event ->
            when (event) {
                is FabCLicked -> eventHandler.handleFabClicked { name ->
                    viewModel.tryUpdateProfile(name)
                }

                is BackgroundClicked -> eventHandler.handleBackgroundClicked()
            }
        }
    }

    // onCreateView --------------------------------------------------------------------------------
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserNameBinding.inflate(layoutInflater)
        _stateHandler = UserNameFragStateHandler(this)
        _effectHandler = UserNameFragEffectHandler(this)
        _eventHandler = UserNameFragEventFragHandler(this)
        return binding.root
    }

    // onViewCreated -------------------------------------------------------------------------------
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBackgroundClickListener()
        setFabClickListener()
    }

    /**
     * Sets the listener for the background click event.
     */
    private fun setBackgroundClickListener() {
        binding.fragUserNameMain.setOnClickListener {
            viewModel.setEvent(BackgroundClicked)
        }
    }

    /**
     * Sets the listener for the FAB click event.
     */
    private fun setFabClickListener() {
        binding.fragUserNameFab.setOnClickListener {
            viewModel.setEvent(FabCLicked)
        }
    }

    // onDestroyView -------------------------------------------------------------------------------
    override fun onDestroyView() {
        super.onDestroyView()
        _stateHandler = null
        _effectHandler = null
        _eventHandler = null
        _binding = null
    }

}