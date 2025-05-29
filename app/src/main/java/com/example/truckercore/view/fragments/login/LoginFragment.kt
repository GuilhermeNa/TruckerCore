package com.example.truckercore.view.fragments.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.truckercore._utils.expressions.navController
import com.example.truckercore.databinding.FragmentLoginBinding
import com.example.truckercore.model.configs.flavor.FlavorService
import com.example.truckercore.view.dialogs.LoadingDialog
import com.example.truckercore.view.fragments._base.CloseAppFragment
import com.example.truckercore.view.fragments.login.navigator.LoginNavigator
import com.example.truckercore.view.fragments.login.navigator.LoginNavigatorImpl
import com.example.truckercore.view.fragments.login.ui_handler.LoginUiStateHandler
import com.example.truckercore.view.fragments.login.ui_handler.LoginUiStateHandlerImpl
import com.example.truckercore.view_model.view_models.login.LoginEffect
import com.example.truckercore.view_model.view_models.login.LoginEvent
import com.example.truckercore.view_model.view_models.login.LoginViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : CloseAppFragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val flavorService: FlavorService by inject()

    private val navigator: LoginNavigator by lazy {
        val strategy = flavorService.getLoginNavigatorStrategy(navController())
        LoginNavigatorImpl(strategy)
    }

/*
    private val stateHandler: LoginUiStateHandler by lazy {
        LoginUiStateHandlerImpl()
    }
*/





    private val navigationHandler by lazy { LoginNavigationHandler() }

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
                setEventManager()

            }
        }
    }

    private fun CoroutineScope.setUiStateManager() {
        launch {
            /*viewModel.state.collect { state ->
                stateHandler?.handleEmail(state.email)
                stateHandler?.handlePassword(state.password)
                stateHandler?.handleEnterButton(state.buttonEnabled)

                if (state.loading) dialog.show()
                else dialog.dismissIfShowing()
            }*/
        }
    }

    private suspend fun setEventManager() {
        viewModel.effect.collect { effect ->
            if (effect is LoginEffect.ClearFocusAndHideKeyboard) {
               // stateHandler.hideKeyboardAndClearFocus(this)
                return@collect
            }

            if (effect.isFragmentNavigation()) {
             /*   val direction = navigationHandler.getDirection(effect)
                navigateToDirection(direction)*/
                return@collect
            }

         //   val intent = navigationHandler.getIntent(effect, requireContext())
        //    navigateToActivity(intent, true)
        }
    }

    //----------------------------------------------------------------------------------------------
    // onCreateView
    //----------------------------------------------------------------------------------------------
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(layoutInflater)
       /* _stateHandler = LoginUiStateHandlerImpl(binding)*/
        return binding.root
    }

    //----------------------------------------------------------------------------------------------
    // onViewCreated
    //----------------------------------------------------------------------------------------------
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBackgroundViewsClickListener()
        setEnterButtonClickListener()
        setNewAccountButtonClickListener()
        setForgetPasswordButtonClickListener()
        setEmailChangeListener()
        setPasswordChangeListener()
    }

    private fun setEmailChangeListener() {
        binding.fragLoginEmailText.addTextChangedListener { editable ->
            val text = editable.toString()
            viewModel.onEvent(LoginEvent.UiEvent.EmailFieldChanged(text))
        }
    }

    private fun setPasswordChangeListener() {
        binding.fragLoginPasswordText.addTextChangedListener { editable ->
            val text = editable.toString()
            viewModel.onEvent(LoginEvent.UiEvent.PasswordFieldChanged(text))
        }
    }

    private fun setBackgroundViewsClickListener() {
        binding.fragLoginBackground.setOnClickListener {
            viewModel.onEvent(LoginEvent.UiEvent.BackGroundCLick)
        }
        binding.fragLoginCard.setOnClickListener {
            viewModel.onEvent(LoginEvent.UiEvent.BackGroundCLick)
        }
    }

    private fun setEnterButtonClickListener() {
        binding.fragLoginEnterButton.setOnClickListener {
            viewModel.onEvent(LoginEvent.UiEvent.EnterButtonClick)
        }
    }

    private fun setNewAccountButtonClickListener() {
        binding.fragLoginNewAccountButton.setOnClickListener {
            viewModel.onEvent(LoginEvent.UiEvent.NewAccountButtonClick)
        }
    }

    private fun setForgetPasswordButtonClickListener() {
        binding.fragLoginForgetPasswordButton.setOnClickListener {
            viewModel.onEvent(LoginEvent.UiEvent.ForgetPasswordButtonClick)
        }
    }

    //----------------------------------------------------------------------------------------------
    // onDestroyView
    //----------------------------------------------------------------------------------------------
    override fun onDestroyView() {
        super.onDestroyView()
       // _stateHandler = null
        _binding = null
    }

}