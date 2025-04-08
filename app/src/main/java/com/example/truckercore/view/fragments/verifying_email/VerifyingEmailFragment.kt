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
import com.example.truckercore.view.fragments.base.CloseAppFragment
import com.example.truckercore.view_model.view_models.verifying_email.VerifyingEmailEvent.ResendButtonClicked
import com.example.truckercore.view_model.view_models.verifying_email.VerifyingEmailFragState
import com.example.truckercore.view_model.view_models.verifying_email.VerifyingEmailFragState.ResendFunction
import com.example.truckercore.view_model.view_models.verifying_email.VerifyingEmailFragState.ResendFunction.ResendBlocked
import com.example.truckercore.view_model.view_models.verifying_email.VerifyingEmailFragState.ResendFunction.ResendEnabled
import com.example.truckercore.view_model.view_models.verifying_email.VerifyingEmailReceivedArgs
import com.example.truckercore.view_model.view_models.verifying_email.VerifyingEmailViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class VerifyingEmailFragment : CloseAppFragment() {

    private var _binding: FragmentVerifyingEmailBinding? = null
    private val binding get() = _binding!!

    // Args ----------------------------------------------------------------------------------------
    private val args: VerifyingEmailFragmentArgs by navArgs()
    private val receivedArgs: VerifyingEmailReceivedArgs by lazy {
        VerifyingEmailReceivedArgs(email = args.email, emailSent = args.emailSent)
    }

    // ViewModel -----------------------------------------------------------------------------------
    private val viewModel: VerifyingEmailViewModel by viewModel { parametersOf(receivedArgs) }

    // StateHandler---------------------------------------------------------------------------------
    private var stateHandler: VerifyingEmailFragStateHandler? = null

    //----------------------------------------------------------------------------------------------
    // onCreate()
    //----------------------------------------------------------------------------------------------
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
                    is VerifyingEmailFragState.EmailNotSend -> stateHandler?.setEmailNotSendState()
                    is VerifyingEmailFragState.Success -> handleSuccessState()
                    is VerifyingEmailFragState.Error -> stateHandler?.setErrorState(state.message)
                }
            }
        }
    }

    private fun handleSuccessState() {
        navigateTo(TODO("navegar para a tela home"))
    }

    private suspend fun handleEMailSentState(resendType: ResendFunction) {
        when (resendType) {
            ResendBlocked -> stateHandler?.setEmailSentWIthButtonDisabled()
            ResendEnabled -> stateHandler?.setEmailSentWithButtonEnabled()
        }
    }

    private fun CoroutineScope.setCounterStateManager() {
        launch {
            viewModel.counterState.collect { counter ->
                    stateHandler?.updateCounter(counter)
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

    //----------------------------------------------------------------------------------------------
    // onCreateView()
    //----------------------------------------------------------------------------------------------
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVerifyingEmailBinding.inflate(layoutInflater)
        stateHandler = VerifyingEmailFragStateHandler(binding, args.email)
        return binding.root
    }

    //----------------------------------------------------------------------------------------------
    // onViewCreated()
    //----------------------------------------------------------------------------------------------
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setResendButtonClickListener()
    }

    private fun setResendButtonClickListener() {
        binding.fragVerifyingEmailButtonResend.setOnClickListener {
            viewModel.setEvent(ResendButtonClicked)
        }
    }

}



