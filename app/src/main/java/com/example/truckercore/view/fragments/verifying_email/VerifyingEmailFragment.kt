package com.example.truckercore.view.fragments.verifying_email

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.example.truckercore.databinding.FragmentVerifyingEmailBinding
import com.example.truckercore.view.expressions.navigateTo
import com.example.truckercore.view.expressions.showSnackBarRed
import com.example.truckercore.view.fragments.base.CloseAppFragment
import com.example.truckercore.view_model.view_models.verifying_email.VerifyingEmailEvent.ResendButtonClicked
import com.example.truckercore.view_model.view_models.verifying_email.VerifyingEmailFragData
import com.example.truckercore.view_model.view_models.verifying_email.VerifyingEmailFragState
import com.example.truckercore.view_model.view_models.verifying_email.VerifyingEmailFragState.ResendFunction
import com.example.truckercore.view_model.view_models.verifying_email.VerifyingEmailFragState.ResendFunction.ResendBlocked
import com.example.truckercore.view_model.view_models.verifying_email.VerifyingEmailFragState.ResendFunction.ResendEnabled
import com.example.truckercore.view_model.view_models.verifying_email.VerifyingEmailReceivedArgs
import com.example.truckercore.view_model.view_models.verifying_email.VerifyingEmailViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class VerifyingEmailFragment : CloseAppFragment() {

    // Binding
    private var _binding: FragmentVerifyingEmailBinding? = null
    val binding get() = _binding!!

    // Ui Handler
    private var _uiHandler: UiHandler? = null
    private val uiHandler get() = _uiHandler!!

    // Fragment Args
    private val args: VerifyingEmailFragmentArgs by navArgs()
    private val receivedArgs: VerifyingEmailReceivedArgs by lazy {
        VerifyingEmailReceivedArgs(email = args.email, emailSent = args.emailSent)
    }

    // ViewModel
    private val viewModel: VerifyingEmailViewModel by viewModel { parametersOf(receivedArgs) }

    // onCreate ------------------------------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                setFragmentStateManager()
                setCounterStateManager()
                setFragmentEventManager()
            }
        }
    }

    private fun CoroutineScope.setFragmentStateManager() {
        launch {
            viewModel.fragmentState.collect { state ->
                when (state) {
                    is VerifyingEmailFragState.Initial -> Unit
                    is VerifyingEmailFragState.EmailSent -> handleEMailSentState(state.resendType)
                    is VerifyingEmailFragState.EmailNotSend -> _uiHandler?.setEmailNotSendState()
                    is VerifyingEmailFragState.Success -> handleSuccessState()
                    is VerifyingEmailFragState.Error -> _uiHandler?.setErrorState(state.message)
                }
            }
        }
    }

    private fun handleSuccessState() {
        navigateTo(TODO("navegar para a tela home"))
    }

    private suspend fun handleEMailSentState(resendType: ResendFunction) {
        when (resendType) {
            ResendBlocked -> _uiHandler?.setEmailSentWIthButtonDisabled()
            ResendEnabled -> _uiHandler?.setEmailSentWithButtonEnabled()
        }
    }

    private fun CoroutineScope.setCounterStateManager() {
        launch {
            viewModel.counterState.collect { counter ->
                    _uiHandler?.updateCounter(counter)
            }
        }
    }

    private suspend fun setFragmentEventManager(): Nothing {
        viewModel.fragmentEvent.collect { event ->
            when (event) {
                is ResendButtonClicked -> viewModel.sendVerificationEmail()
            }
        }
    }

    // onCreateView --------------------------------------------------------------------------------
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVerifyingEmailBinding.inflate(layoutInflater)
        _uiHandler = UiHandler(this, args.email)
        return binding.root
    }

    // onViewCreated -------------------------------------------------------------------------------
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setResendButtonClickListener()
    }

    private fun setResendButtonClickListener() {
        binding.fragVerifyingEmailButtonResend.setOnClickListener {
            viewModel.setEvent(ResendButtonClicked)
        }
    }

    // onDestroyView -------------------------------------------------------------------------------
    override fun onDestroyView() {
        super.onDestroyView()
        _uiHandler = null
        _binding = null
    }

    //----------------------------------------------------------------------------------------------
    //
    //----------------------------------------------------------------------------------------------


}

private class UiHandler(fragment: VerifyingEmailFragment, email: String) {

    private val binding = fragment.binding
    private val view = binding.root.rootView
    private val textProvider = VerifyingEmailTextProvider(email)

    //----------------------------------------------------------------------------------------------

    fun setEmailSentWIthButtonDisabled() {
        setButton(enable = false)
        setInState1()
        val viewData = textProvider.invoke(true)
        bindData(viewData)
    }

    suspend fun setEmailSentWithButtonEnabled() {
        val viewData = textProvider.invoke(true)
        bindData(viewData)
        setInState2()
        delay(500)
        setButton(enable = true)

    }

    fun setEmailNotSendState() {
        setInState2()
        val viewData = textProvider.invoke(false)
        bindData(viewData)
    }

    fun setErrorState(message: String) {
        view.showSnackBarRed(message)
    }

    private fun bindData(data: VerifyingEmailFragData) {
        binding.run {
            fragVerifyingEmailTitle.text = data.title
            fragVerifyingEmailMessage.text = data.text
            fragVerifyingEmailSentTo.text = data.email
        }
    }

    private fun setInState1() {
        binding.fragVerifyingEmailMain.transitionToStart()
    }

    private fun setInState2() {
        binding.fragVerifyingEmailMain.transitionToEnd()
    }

    private fun setButton(enable: Boolean) {
        binding.fragVerifyingEmailButtonResend.isEnabled = enable
    }

    fun updateCounter(counter: Int) {
        val value = if (counter >= 10) "$counter" else "0$counter"
        binding.fragVerifyingEmailTimer.text = value
    }

}



