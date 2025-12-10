package com.example.truckercore.layers.presentation.nav_login.view.fragments.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.example.truckercore.core.config.flavor.FlavorService
import com.example.truckercore.core.my_lib.expressions.launchAndRepeatOnFragmentStartedLifeCycle
import com.example.truckercore.databinding.FragmentLoginBinding
import com.example.truckercore.layers.presentation.base.abstractions.view._public.PublicLockedFragment
import com.example.truckercore.layers.presentation.common.LoadingDialog
import com.example.truckercore.layers.presentation.nav_login.view_model.login.LoginViewModel
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : PublicLockedFragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val dialog: LoadingDialog by lazy { LoadingDialog(requireContext()) }

    private val viewModel: LoginViewModel by viewModel()
    private val flavorService: FlavorService by inject()
    private val stateHandler = LoginFragmentUiStateHandler()

    //----------------------------------------------------------------------------------------------
    // OnCreate
    //----------------------------------------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        launchAndRepeatOnFragmentStartedLifeCycle {
            setUiStateManager()
            setEffectManager()
        }
    }

    private fun CoroutineScope.setUiStateManager() {
        launch {
            viewModel.stateFlow.collect { state ->
                stateHandler.handle(state, dialog)
            }
        }
    }

    private fun handleDialog(status: LoginUiState.Status) {
        if (status.isLoading) dialog.show()
        else dialog.dismissIfShowing()
    }

    private suspend fun setEffectManager() {
        viewModel.effectFlow.collect { effect ->
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
            viewModel.onEvent(
                Typing.EmailField(
                    text
                )
            )
        }
    }

    private fun setCheckBoxClickListener() {
        binding.fragLoginCheckbox.setOnCheckedChangeListener { checkBox, _ ->
            val isChecked = checkBox.isChecked
            viewModel.onEvent(
                com.example.truckercore.presentation.viewmodels.view_models.login.LoginEvent.UiEvent.Click.CheckBox(
                    isChecked
                )
            )
        }
    }

    private fun setPasswordChangeListener() {
        binding.fragLoginPasswordText.addTextChangedListener { editable ->
            val text = editable.toString()
            viewModel.onEvent(
                com.example.truckercore.presentation.viewmodels.view_models.login.LoginEvent.UiEvent.Typing.PasswordField(
                    text
                )
            )
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