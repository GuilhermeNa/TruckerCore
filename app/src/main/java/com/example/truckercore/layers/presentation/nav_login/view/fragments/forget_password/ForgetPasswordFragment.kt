package com.example.truckercore.layers.presentation.nav_login.view.fragments.forget_password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.example.truckercore.core.my_lib.expressions.launchAndRepeatOnFragmentStartedLifeCycle
import com.example.truckercore.core.my_lib.expressions.popBackstack
import com.example.truckercore.core.my_lib.expressions.showSuccessSnackbar
import com.example.truckercore.databinding.FragmentForgetPasswordBinding
import com.example.truckercore.layers.presentation.base.abstractions.view._public.PublicFragment
import com.example.truckercore.layers.presentation.common.LoadingDialog
import com.example.truckercore.layers.presentation.nav_login.view_model.forget_password.ForgetPasswordViewModel
import com.example.truckercore.layers.presentation.nav_login.view_model.forget_password.effect.ForgetPasswordFragmentEffect
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Fragment responsible for handling the "Forget Password" feature.
 *
 * This fragment observes the ViewModel's state and effect flows to update the UI
 * and handle side-effects such as navigation or error notifications.
 */
class ForgetPasswordFragment : PublicFragment() {

    private var _binding: FragmentForgetPasswordBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ForgetPasswordViewModel by viewModel()

    /** Lazy-initialized loading dialog shown when sending email. */
    private val dialog by lazy { LoadingDialog(requireContext()) }

    //----------------------------------------------------------------------------------------------
    // Lifecycle OnCreate
    //----------------------------------------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        launchAndRepeatOnFragmentStartedLifeCycle {
            setFragmentStateManager()
            setFragmentEffectManager()
        }
    }

    /**
     * Initializes state flow collector.
     * Observes the ViewModel state and updates UI components accordingly.
     */
    private fun CoroutineScope.setFragmentStateManager() {
        launch {
            viewModel.stateFlow.collect { state ->
                enableError(state.isInvalid)
                enableButton(state.isReadyToSend)
                enableDialog(state.isSending)
            }
        }
    }

    /**
     * Shows or hides an error message in the email input field.
     */
    private fun enableError(enable: Boolean) {
        val error = if (enable) ERROR_MESSAGE else null
        binding.fragForgetPassLayout.error = error
    }

    /**
     * Enables or disables the "Send Email" button based on state.
     */
    private fun enableButton(enabled: Boolean) {
        binding.fragForgetPassButton.isEnabled = enabled
    }

    /**
     * Shows or dismisses a loading dialog depending on sending status.
     */
    private fun enableDialog(sending: Boolean) {
        if (sending) dialog.show()
        else dialog.dismiss()
    }

    /**
     * Initializes effect flow collector.
     * Handles one-time actions like navigation or notifications.
     */
    private suspend fun setFragmentEffectManager() {
        viewModel.effectFlow.collect { effect ->
            when (effect) {
                ForgetPasswordFragmentEffect.NavigateToNoConnection ->
                    navigateToNoConnection(this) {
                        viewModel.sendEmail(emailText())
                    }

                ForgetPasswordFragmentEffect.NavigateToNotification ->
                    navigateToErrorActivity(requireActivity())

                ForgetPasswordFragmentEffect.NavigateBackAndNotifySuccess -> {
                    showSuccessSnackbar(SUCCESS_MESSAGE)
                    popBackstack()
                }
            }
        }
    }

    /**
     * Returns the current text from the email input field.
     */
    private fun emailText() = binding.fragForgetPassText.text.toString()

    //----------------------------------------------------------------------------------------------
    // onCreateView
    //----------------------------------------------------------------------------------------------
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForgetPasswordBinding.inflate(layoutInflater)
        return binding.root
    }

    //----------------------------------------------------------------------------------------------
    // onViewCreated
    //----------------------------------------------------------------------------------------------
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setButtonClickListener()
        setEmailTextChangeListener()
    }

    /**
     * Sets up click listener for the "Send Email" button.
     * Triggers the ViewModel to send the email.
     */
    private fun setButtonClickListener() {
        binding.fragForgetPassButton.setOnClickListener {
            viewModel.sendEmail(emailText())
        }
    }

    /**
     * Sets up text change listener for the email input field.
     * Notifies ViewModel of changes to update the current state.
     */
    private fun setEmailTextChangeListener() {
        binding.fragForgetPassText.addTextChangedListener {
            viewModel.emailChanged(it.toString())
        }
    }

    //----------------------------------------------------------------------------------------------
    // onDestroyView
    //----------------------------------------------------------------------------------------------
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //----------------------------------------------------------------------------------------------
    // Constants
    //----------------------------------------------------------------------------------------------
    private companion object {
        private const val SUCCESS_MESSAGE = "Recovery email sent successfully"
        private const val ERROR_MESSAGE = "Invalid email format"
    }
}