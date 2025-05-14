package com.example.truckercore.view.fragments.forget_password

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.truckercore._utils.expressions.hideKeyboardAndClearFocus
import com.example.truckercore._utils.expressions.navigateToActivity
import com.example.truckercore._utils.expressions.popBackStack
import com.example.truckercore._utils.expressions.showGreenSnackBar
import com.example.truckercore._utils.expressions.showRedSnackBar
import com.example.truckercore.databinding.FragmentForgetPasswordBinding
import com.example.truckercore.view.activities.NotificationActivity
import com.example.truckercore.view.dialogs.LoadingDialog
import com.example.truckercore.view_model.view_models.forget_password.ForgetPasswordEffect
import com.example.truckercore.view_model.view_models.forget_password.ForgetPasswordEvent
import com.example.truckercore.view_model.view_models.forget_password.ForgetPasswordUiState
import com.example.truckercore.view_model.view_models.forget_password.ForgetPasswordViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForgetPasswordFragment : Fragment() {

    private var _binding: FragmentForgetPasswordBinding? = null
    private val binding get() = _binding!!

    private var stateHandler: ForgetPasswordUiStateHandler? = null

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
            viewModel.uiState.collect { state ->
                stateHandler?.handleButton(state.buttonState)
                stateHandler?.handleEmailLayout(state.emailField)

                if(!state.isSendingEmail()) dialog.dismissIfShowing()

                when (state.status) {
                    ForgetPasswordUiState.Status.AwaitingInput -> Unit

                    is ForgetPasswordUiState.Status.CriticalError -> {
                        val intent = NotificationActivity.newInstance(
                            context = requireContext(),
                            title = state.status.uiError.title,
                            message = state.status.uiError.message
                        )
                        navigateToActivity(intent, true)
                    }

                    ForgetPasswordUiState.Status.SendingEmail -> dialog.show()

                    ForgetPasswordUiState.Status.Success -> {
                        showGreenSnackBar(state.successMessage())
                        delay(1500)
                        popBackStack()
                    }
                }

            }
        }
    }

    private suspend fun setEffectManager() {
        viewModel.effect.collect { effect ->
            if(effect is ForgetPasswordEffect.RecoverableError) {
                showRedSnackBar(effect.message)
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
        stateHandler = ForgetPasswordUiStateHandler(
            binding.fragForgetPassLayout,
            binding.fragForgetPassButton
        )
        return binding.root
    }

    //----------------------------------------------------------------------------------------------
    // OnViewCreated
    //----------------------------------------------------------------------------------------------
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setButtonClickListener()
        setEmailTextChangeListener()
        setBackgroundTouchListener()
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

    @SuppressLint("ClickableViewAccessibility")
    private fun setBackgroundTouchListener() {
        binding.fragForgetPassBg.setOnTouchListener { _, _ ->
            hideKeyboardAndClearFocus(binding.fragForgetPassLayout)
            true
        }
    }

    private fun setButtonClickListener() {
        binding.fragForgetPassButton.setOnClickListener {
            viewModel.onEvent(ForgetPasswordEvent.UiEvent.SendButtonClicked)
        }
    }

    private fun setEmailTextChangeListener() {
        binding.fragForgetPassText.addTextChangedListener {
            val text = it.toString()
            viewModel.onEvent(ForgetPasswordEvent.UiEvent.EmailTextChange(text))
        }
    }

    //----------------------------------------------------------------------------------------------
    // OnDestroyView
    //----------------------------------------------------------------------------------------------
    override fun onDestroyView() {
        super.onDestroyView()
        stateHandler = null
        _binding = null
    }

}