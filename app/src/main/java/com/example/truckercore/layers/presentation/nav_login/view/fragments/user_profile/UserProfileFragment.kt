package com.example.truckercore.layers.presentation.nav_login.view.fragments.user_profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.example.truckercore.core.config.flavor.FlavorService
import com.example.truckercore.core.my_lib.expressions.launchAndRepeatOnFragmentStartedLifeCycle
import com.example.truckercore.core.my_lib.expressions.navigateToDirection
import com.example.truckercore.databinding.FragmentUserNameBinding
import com.example.truckercore.layers.presentation.base.abstractions.view._public.PublicLockedFragment
import com.example.truckercore.layers.presentation.common.LoadingDialog
import com.example.truckercore.layers.presentation.viewmodels.view_models.user_name.UserProfileViewModel
import com.example.truckercore.layers.presentation.viewmodels.view_models.user_name.effect.UserProfileFragmentEffect
import com.example.truckercore.layers.presentation.viewmodels.view_models.user_name.event.UserProfileFragmentEvent
import com.example.truckercore.layers.presentation.viewmodels.view_models.user_name.state.UserProfileFragmentState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Fragment responsible for managing the the user profile creation.
 *
 * It wires the UI to the [UserProfileViewModel], reacts to user input,
 * and handles both [UserProfileFragmentState] updates and [UserProfileFragmentEffect] events.
 *
 * The screen consists of:
 * - A text field for entering the user's name.
 * - A FAB used to submit the updated name.
 * - A loading dialog shown during processing states.
 */
class UserProfileFragment : PublicLockedFragment() {

    // View Binding ------------------------------------------------------------------------------
    private var _binding: FragmentUserNameBinding? = null
    val binding get() = _binding!!

    // ViewModel and Dependencies ----------------------------------------------------------------
    private val flavorService by inject<FlavorService>()
    private val viewModel: UserProfileViewModel by viewModel()
    private val stateHandler = UserProfileFragmentStateHandler()
    private val dialog by lazy { LoadingDialog(requireContext()) }

    // Navigation ---------------------------------------------------------------------------------
    private val loginFragmentDirection =
        UserProfileFragmentDirections.actionGlobalLoginFragment()

    //----------------------------------------------------------------------------------------------
    // Lifecycle - onCreate()
    //----------------------------------------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onInitializing(savedInstanceState) { viewModel.initialize() }

        launchAndRepeatOnFragmentStartedLifeCycle {
            setFragmentStateManager()
            setFragmentEffectManager()
        }
    }

    /**
     * Observes [UserProfileFragmentState] values and updates the UI through [UserProfileFragmentStateHandler].
     */
    private fun CoroutineScope.setFragmentStateManager() {
        launch {
            viewModel.stateFlow.collect { state ->
                stateHandler.handleState(dialog, state)
            }
        }
    }

    /**
     * Handles one-time [UserProfileFragmentEffect] events such as navigation,
     * retry flows, and error redirections.
     */
    private suspend fun setFragmentEffectManager() {
        viewModel.effectFlow.collect { effect ->
            when (effect) {

                UserProfileFragmentEffect.Navigation.ToCheckIn ->
                    flavorService.navigateToCheckIn(requireActivity())

                UserProfileFragmentEffect.Navigation.ToLogin ->
                    navigateToDirection(loginFragmentDirection)

                UserProfileFragmentEffect.Navigation.ToNotification ->
                    navigateToErrorActivity(requireActivity())

                UserProfileFragmentEffect.Navigation.ToNoConnection ->
                    navigateToNoConnection(this) {
                        viewModel.onEvent(UserProfileFragmentEvent.Retry)
                    }

                else -> Unit
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
        _binding = FragmentUserNameBinding.inflate(layoutInflater)
        stateHandler.initialize(binding)
        return binding.root
    }

    //----------------------------------------------------------------------------------------------
    // Lifecycle - onViewCreated()
    //----------------------------------------------------------------------------------------------
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setNameChangedListener()
        setFabClickListener()
    }

    private fun setNameChangedListener() {
        binding.fragUserNameText.addTextChangedListener { editable ->
            val text = editable.toString()
            viewModel.onEvent(UserProfileFragmentEvent.TextChanged(text))
        }
    }

    private fun setFabClickListener() {
        binding.fragUserNameFab.setOnClickListener {
            viewModel.onEvent(UserProfileFragmentEvent.FabClicked)
        }
    }

    //----------------------------------------------------------------------------------------------
    // Lifecycle - onDestroyView()
    //----------------------------------------------------------------------------------------------
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}