package com.example.truckercore.layers.presentation.nav_login.view.fragments.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.truckercore.core.expressions.hideKeyboardAndClearFocus
import com.example.truckercore.core.expressions.logEffect
import com.example.truckercore.core.expressions.logState
import com.example.truckercore.core.expressions.navController
import com.example.truckercore.core.expressions.showRedSnackBar
import com.example.truckercore.databinding.FragmentLoginBinding
import com.example.truckercore.presentation._shared._base.fragments.CloseAppFragment
import com.example.truckercore.presentation._shared.views.dialogs.LoadingDialog
import com.example.truckercore.presentation.nav_login.fragments.login.navigator.LoginFragmentStrategy
import com.example.truckercore.presentation.nav_login.fragments.login.navigator.LoginNavigator
import com.example.truckercore.presentation.nav_login.fragments.login.ui_handler.LoginUiStateHandler
import com.example.truckercore.presentation.nav_login.fragments.login.ui_handler.LoginUiStateHandlerImpl
import com.example.truckercore.domain.view_models.login.LoginViewModel
import com.example.truckercore.domain.view_models.login.state.LoginUiState
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : CloseAppFragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val flavorService: com.example.truckercore.core.config.flavor.FlavorService by inject()
    private val strategy: LoginFragmentStrategy by lazy {
        flavorService.getLoginFragmentStrategy(navController())
    }

    private val navigator: LoginNavigator by lazy {
        com.example.truckercore.layers.presentation.nav_login.fragments.login.navigator.LoginNavigatorImpl(
            strategy
        )
    }
    private val stateHandler: LoginUiStateHandler by lazy { LoginUiStateHandlerImpl() }

    private val dialog: LoadingDialog by lazy { LoadingDialog(requireContext()) }

    private val viewModel: LoginViewModel by viewModel()

    //----------------------------------------------------------------------------------------------
    // OnCreate
    //----------------------------------------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                setUiStateManager()
                setEffectManager()
            }
        }
    }

    private fun CoroutineScope.setUiStateManager() {
        launch {
            viewModel.stateFlow.collect { state ->
                logState(this@LoginFragment, state)
                stateHandler.handleEmailComponent(state.emailComponent)
                stateHandler.handlePasswordComponent(state.passComponent)
                stateHandler.handleEnterBtnComponent(state.enterBtnComponent)
                stateHandler.handleNewAccountBtnComponent(strategy.getNewAccountButtonComponent())
                handleDialog(state.status)
            }
        }
    }

    private fun handleDialog(status: LoginUiState.Status) {
        if (status.isLoading) dialog.show()
        else dialog.dismissIfShowing()
    }

    private suspend fun setEffectManager() {
        viewModel.effectFlow.collect { effect ->
            logEffect(this@LoginFragment, effect)

            when (effect) {
                com.example.truckercore.presentation.viewmodels.view_models.login.effect.LoginEffect.ClearFocusAndHideKeyboard ->
                    hideKeyboardAndClearFocus(*getFocusableViews())

                com.example.truckercore.presentation.viewmodels.view_models.login.effect.LoginEffect.NavigateToForgetPassword ->
                    navigator.navigateToForgetPassword()

                com.example.truckercore.presentation.viewmodels.view_models.login.effect.LoginEffect.NavigateToNewUser ->
                    navigator.navigateToEmailAuth()

                is com.example.truckercore.presentation.viewmodels.view_models.login.effect.LoginEffect.ShowToast ->
                    showRedSnackBar(effect.message)

                com.example.truckercore.presentation.viewmodels.view_models.login.effect.LoginEffect.NavigateToMain ->
                    flavorService.navigateToMain(requireActivity())

                com.example.truckercore.presentation.viewmodels.view_models.login.effect.LoginEffect.NavigateToNotification ->
                    navigator.navigateToNotification(requireActivity())
            }
        }
    }

    private fun getFocusableViews(): Array<TextInputLayout> = arrayOf(
        binding.fragLoginEmailLayout,
        binding.fragLoginPasswordLayout
    )

    //----------------------------------------------------------------------------------------------
    // onCreateView
    //----------------------------------------------------------------------------------------------
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(layoutInflater)
        stateHandler.initialize(binding)
        return binding.root
    }

    //----------------------------------------------------------------------------------------------
    // onViewCreated
    //----------------------------------------------------------------------------------------------
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBackgroundViewsClickListener()
        setCheckBoxClickListener()
        setEnterButtonClickListener()
        setNewAccountButtonClickListener()
        setForgetPasswordButtonClickListener()
        setEmailChangeListener()
        setPasswordChangeListener()
    }

    private fun setEmailChangeListener() {
        binding.fragLoginEmailText.addTextChangedListener { editable ->
            val text = editable.toString()
            viewModel.onEvent(com.example.truckercore.presentation.viewmodels.view_models.login.LoginEvent.UiEvent.Typing.EmailField(text))
        }
    }

    private fun setCheckBoxClickListener() {
        binding.fragLoginCheckbox.setOnCheckedChangeListener { checkBox, _ ->
            val isChecked = checkBox.isChecked
            viewModel.onEvent(com.example.truckercore.presentation.viewmodels.view_models.login.LoginEvent.UiEvent.Click.CheckBox(isChecked))
        }
    }

    private fun setPasswordChangeListener() {
        binding.fragLoginPasswordText.addTextChangedListener { editable ->
            val text = editable.toString()
            viewModel.onEvent(com.example.truckercore.presentation.viewmodels.view_models.login.LoginEvent.UiEvent.Typing.PasswordField(text))
        }
    }

    private fun setBackgroundViewsClickListener() {
        binding.fragLoginBackground.setOnClickListener {
            viewModel.onEvent(com.example.truckercore.presentation.viewmodels.view_models.login.LoginEvent.UiEvent.Click.Background)
        }
        binding.fragLoginCard.setOnClickListener {
            viewModel.onEvent(com.example.truckercore.presentation.viewmodels.view_models.login.LoginEvent.UiEvent.Click.Background)
        }
    }

    private fun setEnterButtonClickListener() {
        binding.fragLoginEnterButton.setOnClickListener {
            viewModel.onEvent(com.example.truckercore.presentation.viewmodels.view_models.login.LoginEvent.UiEvent.Click.EnterButton)
        }
    }

    private fun setNewAccountButtonClickListener() {
        binding.fragLoginNewAccountButton.setOnClickListener {
            viewModel.onEvent(com.example.truckercore.presentation.viewmodels.view_models.login.LoginEvent.UiEvent.Click.NewAccountButton)
        }
    }

    private fun setForgetPasswordButtonClickListener() {
        binding.fragLoginForgetPasswordButton.setOnClickListener {
            viewModel.onEvent(com.example.truckercore.presentation.viewmodels.view_models.login.LoginEvent.UiEvent.Click.RecoverPasswordButton)
        }
    }

    //----------------------------------------------------------------------------------------------
    // onDestroyView
    //----------------------------------------------------------------------------------------------
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}