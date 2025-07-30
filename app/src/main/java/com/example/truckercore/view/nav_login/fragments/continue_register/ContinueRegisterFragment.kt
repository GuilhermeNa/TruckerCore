package com.example.truckercore.view.nav_login.fragments.continue_register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.truckercore._shared.expressions.logEffect
import com.example.truckercore._shared.expressions.logState
import com.example.truckercore._shared.expressions.showRedSnackBar
import com.example.truckercore.databinding.FragmentContinueRegisterBinding
import com.example.truckercore.model.configs.flavor.FlavorService
import com.example.truckercore.view._shared._base.fragments.CloseAppFragment
import com.example.truckercore.view._shared.expressions.launchOnFragmentLifecycle
import com.example.truckercore.view_model.view_models.continue_register.ContinueRegisterViewModel
import com.example.truckercore.view_model.view_models.continue_register.effect.ContinueRegisterEffect
import com.example.truckercore.view_model.view_models.continue_register.event.ContinueRegisterEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ContinueRegisterFragment : CloseAppFragment() {

    private var _binding: FragmentContinueRegisterBinding? = null
    private val binding get() = _binding!!

    private val navigator: ContinueRegisterNavigator by lazy {
        val flavorService: FlavorService by inject()
        ContinueRegisterNavigator(findNavController(), flavorService)
    }

    private val stateHandler: ContinueRegisterStateHandler by lazy {
        ContinueRegisterStateHandler()
    }

    private val viewModel: ContinueRegisterViewModel by viewModel()

    // ---------------------------------------------------------------------------------------------
    // Lifecycle - OnCreate
    // ---------------------------------------------------------------------------------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initialize()
        launchOnFragmentLifecycle {
            setStateManager()
            setEffectManager()
        }
    }

    private fun CoroutineScope.setStateManager() {
        launch {
            viewModel.stateFlow.collect { state ->
                logState(this@ContinueRegisterFragment, state)
                if (state.isIdle()) {
                    stateHandler.handleUiComponents(requireContext(), state)
                    stateHandler.replaceShimmerForContent()
                }
            }
        }
    }

    private suspend fun setEffectManager() {
        viewModel.effectFlow.collect { effect ->
            logEffect(this@ContinueRegisterFragment, effect)
            when (effect) {
                ContinueRegisterEffect.Navigation.ToEmailAuth ->
                    navigator.navigateToEmailAuth()

                ContinueRegisterEffect.Navigation.ToLogin ->
                    navigator.navigateToLogin()

                ContinueRegisterEffect.Navigation.ToNotification ->
                    navigator.navigateToNotification(requireActivity())

                ContinueRegisterEffect.Navigation.ToUserName ->
                    navigator.navigateToUserName()

                ContinueRegisterEffect.Navigation.ToVerifyEmail ->
                    navigator.navigateToVerifyEmail()

                is ContinueRegisterEffect.ShowErrorMessage ->
                    showRedSnackBar(effect.message)
            }
        }
    }

    // ---------------------------------------------------------------------------------------------
    // Lifecycle - View Binding
    // ---------------------------------------------------------------------------------------------

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContinueRegisterBinding.inflate(layoutInflater)
        stateHandler.initialize(binding)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnContinueCLickListener()
        setOnNewEmailClickListener()
    }

    private fun setOnContinueCLickListener() {
        binding.fragContinueRegisterFinishRegisterButton.setOnClickListener {
            viewModel.onEvent(ContinueRegisterEvent.UiEvent.Click.FinishRegisterButton)
        }
    }

    private fun setOnNewEmailClickListener() {
        binding.fragContinueRegisterNewEmailButton.setOnClickListener {
            viewModel.onEvent(ContinueRegisterEvent.UiEvent.Click.NewRegisterButton)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}