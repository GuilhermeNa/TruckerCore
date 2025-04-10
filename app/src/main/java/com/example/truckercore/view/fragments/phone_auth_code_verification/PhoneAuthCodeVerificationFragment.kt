package com.example.truckercore.view.fragments.phone_auth_code_verification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.navGraphViewModels
import com.example.truckercore.R
import com.example.truckercore.databinding.FragmentPhoneAuthCodeVerificationBinding
import com.example.truckercore.model.shared.utils.sealeds.Response
import com.example.truckercore.view.expressions.hideKeyboard
import com.example.truckercore.view_model.view_models.phone_auth.PhoneAuthFragState
import com.example.truckercore.view_model.view_models.phone_auth.PhoneAuthSharedViewModel
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.ozcanalasalvar.otp_view.view.OtpView
import kotlinx.coroutines.launch

class PhoneAuthCodeVerificationFragment : Fragment() {

    private var _binding: FragmentPhoneAuthCodeVerificationBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: PhoneAuthSharedViewModel by navGraphViewModels(R.id.nav_graph_login)

    // onCreateView() ------------------------------------------------------------------------------
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhoneAuthCodeVerificationBinding.inflate(layoutInflater)
        return binding.root
    }

    // onViewCreated -------------------------------------------------------------------------------
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setMessageText()
        setMainLayoutClickListener()
        setOtpChangedListener()
        setButtonClickListener()
    }

    private fun setMessageText() {
        val message = "Você receberá um código de verificação " +
                "por mensagem de texto no número: ${sharedViewModel.phoneNumber}."
        binding.fragPhoneAuthCodeVerificationMessage.text = message
    }

    private fun setMainLayoutClickListener() {
        binding.fragPhoneAuthVerificationMain.setOnClickListener {
            hideKeyboardAndClearOtpViewFocus()
        }
    }

    private fun hideKeyboardAndClearOtpViewFocus(): Unit = with(binding) {
        fragPhoneAuthCodeVerificationOtp.clearFocus()
        hideKeyboard()
    }

    private fun setOtpChangedListener() {
        binding.fragPhoneAuthCodeVerificationOtp.setTextChangeListener(
            object : OtpView.ChangeListener {
                override fun onTextChange(value: String, completed: Boolean) {
                    sharedViewModel.storeReceivedCode(value)
                    updateButtonState(completed)
                }
            })
    }

    private fun updateButtonState(completed: Boolean) {
        binding.fragPhoneAuthCodeVerificationButton.isEnabled = completed
    }

    private fun setButtonClickListener(): Unit = with(binding) {
        fragPhoneAuthCodeVerificationButton.setOnClickListener {
            hideKeyboardAndClearOtpViewFocus()
            verifyCodeAndAuth()
        }
    }

    private fun verifyCodeAndAuth() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                sharedViewModel.getCredentialAndAuthenticateUser().let {

                }
            }
        }
    }

    // onDestroyView() -----------------------------------------------------------------------------
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}