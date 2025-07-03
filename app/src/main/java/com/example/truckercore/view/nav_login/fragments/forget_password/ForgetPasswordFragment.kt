package com.example.truckercore.view.nav_login.fragments.forget_password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.truckercore._shared.expressions.hideKeyboardAndClearFocus
import com.example.truckercore._shared.expressions.logEffect
import com.example.truckercore._shared.expressions.logState
import com.example.truckercore._shared.expressions.showGreenSnackBar
import com.example.truckercore._shared.expressions.showRedSnackBar
import com.example.truckercore.databinding.FragmentForgetPasswordBinding
import com.example.truckercore.view._shared._base.fragments.BaseFragment
import com.example.truckercore.view._shared.views.dialogs.LoadingDialog
import com.example.truckercore.view_model.view_models.forget_password.ForgetPasswordEvent
import com.example.truckercore.view_model.view_models.forget_password.ForgetPasswordViewModel
import com.example.truckercore.view_model.view_models.forget_password.effect.ForgetPasswordEffect
import com.example.truckercore.view_model.view_models.forget_password.state.ForgetPasswordUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForgetPasswordFragment : BaseFragment() {

    private var _binding: FragmentForgetPasswordBinding? = null
    private val binding get() = _binding!!

    private val stateHandler by lazy { ForgetPasswordUiStateHandler() }

    private val navigator by lazy { ForgetPasswordNavigator(findNavController()) }

    private val dialog by lazy { LoadingDialog(requireContext()) }

    private val viewModel: ForgetPasswordViewModel by viewModel()

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
                logState(this@ForgetPasswordFragment, state)
                stateHandler.handlePasswordComponent(state.passwordComponent)
                stateHandler.handleButtonComponent(state.buttonComponent)
                handleStatus(state.status)
            }
        }
    }

    private fun handleStatus(status: ForgetPasswordUiState.Status) {
        if (status.isLoading()) dialog.show()
        else dialog.dismissIfShowing()
    }

    private suspend fun setEffectManager() {
        viewModel.effectFlow.collect { effect ->
            logEffect(this@ForgetPasswordFragment, effect)

            when (effect) {
                ForgetPasswordEffect.ClearKeyboardAndFocus ->
                    hideKeyboardAndClearFocus(binding.fragForgetPassLayout)

                is ForgetPasswordEffect.ShowMessage ->
                    showRedSnackBar(effect.message)

                ForgetPasswordEffect.Navigate.BackStack -> lifecycleScope.launch {
                    dialog.dismissIfShowing()
                    showGreenSnackBar("Email de recuperação enviado")
                    delay(1000)
                    navigator.popBackStack()
                }

                ForgetPasswordEffect.Navigate.ToNotification ->
                    navigator.navigateToNotification(requireActivity())
            }
        }
    }

    //----------------------------------------------------------------------------------------------
    // OnCreateView
    //----------------------------------------------------------------------------------------------
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForgetPasswordBinding.inflate(layoutInflater)
        stateHandler.initialize(binding)
        return binding.root
    }

    //----------------------------------------------------------------------------------------------
    // OnViewCreated
    //----------------------------------------------------------------------------------------------
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setButtonClickListener()
        setEmailTextChangeListener()
        setBackgroundClickListener()
        setKeyboardActionDoneListener()
    }

    private fun setKeyboardActionDoneListener() {
        binding.fragForgetPassText.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                v.clearFocus()
            }
            false
        }
    }

    private fun setBackgroundClickListener() {
        binding.fragForgetPassBg.setOnClickListener {
            viewModel.onEvent(ForgetPasswordEvent.UiEvent.Click.Background)
        }
    }

    private fun setButtonClickListener() {
        binding.fragForgetPassButton.setOnClickListener {
            viewModel.onEvent(ForgetPasswordEvent.UiEvent.Click.SendButton)
        }
    }

    private fun setEmailTextChangeListener() {
        binding.fragForgetPassText.addTextChangedListener {
            val text = it.toString()
            viewModel.onEvent(ForgetPasswordEvent.UiEvent.Typing.EmailText(text))
        }
    }

    //----------------------------------------------------------------------------------------------
    // OnDestroyView
    //----------------------------------------------------------------------------------------------
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}