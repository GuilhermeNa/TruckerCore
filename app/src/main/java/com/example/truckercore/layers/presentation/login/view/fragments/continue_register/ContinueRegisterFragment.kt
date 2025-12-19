package com.example.truckercore.layers.presentation.login.view.fragments.continue_register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.truckercore.core.my_lib.expressions.launchAndRepeatOnFragmentStartedLifeCycle
import com.example.truckercore.core.my_lib.expressions.navigateToDirection
import com.example.truckercore.core.my_lib.expressions.showWarningSnackbar
import com.example.truckercore.databinding.FragmentContinueRegisterBinding
import com.example.truckercore.layers.domain.base.enums.RegistrationStatus
import com.example.truckercore.layers.presentation.base.abstractions.view._public.PublicLockedFragment
import com.example.truckercore.layers.presentation.login.view_model.continue_register.ContinueRegisterViewModel
import com.example.truckercore.layers.presentation.login.view_model.continue_register.helpers.ContinueRegisterFragmentEffect
import com.example.truckercore.layers.presentation.login.view_model.continue_register.helpers.ContinueRegisterFragmentEvent
import com.example.truckercore.presentation.nav_login.fragments.continue_register.ContinueRegisterFragmentDirections
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Fragment responsible for displaying the [RegistrationStatus] of the user registration flow.
 *
 * It connects the UI with the [ContinueRegisterViewModel], listens to user interactions,
 * and reacts to updates emitted through [ContinueRegisterFragmentState] and
 * [ContinueRegisterFragmentEffect].
 *
 * The screen consists of:
 * - Text fields that display the user's registration information such as email,
 *   email verification status, and username registration status.
 * - Icons on the left side of each text field indicating completion (✔) or pending (✘) states.
 * - A shimmer layout shown while the screen is in the loading state.
 * - A primary button that advances to the next required registration step.
 * - A secondary button that restarts the registration process, allowing the user
 *   to enter a new email.
 */
class ContinueRegisterFragment : PublicLockedFragment() {

    // View Binding
    private var _binding: FragmentContinueRegisterBinding? = null
    private val binding get() = _binding!!

    // ViewModel and State Handler
    private val viewModel: ContinueRegisterViewModel by viewModel()
    private val stateHandler = ContinueRegisterStateHandler()

    // Navigation Directions
    private val emailAuthFragmentDirection =
        ContinueRegisterFragmentDirections.actionContinueRegisterFragmentToEmailAuthFragment()
    private val verifyEmailFragmentDirection =
        ContinueRegisterFragmentDirections.actionContinueRegisterFragmentToVerifyingEmailFragment()
    private val userNameFragmentDirection =
        ContinueRegisterFragmentDirections.actionContinueRegisterFragmentToUserNameFragment()

    // ---------------------------------------------------------------------------------------------
    // Lifecycle - onCreate()
    // ---------------------------------------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Runs initialization logic only once, even during fragment recreation
        onInitializing(savedInstanceState) { viewModel.initialize() }

        // Starts observing state and effects once the fragment reaches STARTED state
        launchAndRepeatOnFragmentStartedLifeCycle {
            setFragmentStateManager()
            setFragmentEffectManager()
        }
    }

    /**
     * Observes state updates emitted by the [ContinueRegisterViewModel]. When a new
     * [ContinueRegisterFragmentState] is received, the Ui is updated.
     */
    private fun CoroutineScope.setFragmentStateManager() {
        launch {
            viewModel.stateFlow.collect { state ->
                stateHandler.handleState(requireContext(), state)
            }
        }
    }

    /**
     * Observes one-time [ContinueRegisterFragmentEffect]s emitted by the ViewModel
     * and performs side effects such as navigation or displaying temporary messages.
     */
    private suspend fun setFragmentEffectManager() {
        viewModel.effectFlow.collect { effect ->
            when (effect) {
                is ContinueRegisterFragmentEffect.Navigation ->
                    handleNavigationEffect(effect)

                is ContinueRegisterFragmentEffect.WarningToast ->
                    showWarningSnackbar(effect.message)
            }
        }
    }

    private fun handleNavigationEffect(effect: ContinueRegisterFragmentEffect.Navigation) {
        when (effect) {
            ContinueRegisterFragmentEffect.Navigation.ToEmailAuth ->
                navigateToDirection(emailAuthFragmentDirection)

            ContinueRegisterFragmentEffect.Navigation.ToUserName ->
                navigateToDirection(userNameFragmentDirection)

            ContinueRegisterFragmentEffect.Navigation.ToVerifyEmail ->
                navigateToDirection(verifyEmailFragmentDirection)

            ContinueRegisterFragmentEffect.Navigation.ToNotification ->
                navigateToErrorActivity(requireActivity())

            ContinueRegisterFragmentEffect.Navigation.ToNoConnection ->
                navigateToNoConnection(this) { viewModel.initialize() }
        }
    }

    // ---------------------------------------------------------------------------------------------
    // Lifecycle - onCreateView()
    // ---------------------------------------------------------------------------------------------
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContinueRegisterBinding.inflate(layoutInflater)
        stateHandler.initialize(binding)
        return binding.root
    }

    // ---------------------------------------------------------------------------------------------
    // Lifecycle - onViewCreated()
    // ---------------------------------------------------------------------------------------------
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnContinueClickListener()
        setOnNewEmailClickListener()
    }

    private fun setOnContinueClickListener() {
        binding.fragContinueRegisterFinishRegisterButton.setOnClickListener {
            viewModel.onEvent(ContinueRegisterFragmentEvent.Click.ContinueRegisterButton)
        }
    }

    private fun setOnNewEmailClickListener() {
        binding.fragContinueRegisterNewEmailButton.setOnClickListener {
            viewModel.onEvent(ContinueRegisterFragmentEvent.Click.NewRegisterButton)
        }
    }

    // ---------------------------------------------------------------------------------------------
    // Lifecycle - onDestroyView()
    // ---------------------------------------------------------------------------------------------
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}