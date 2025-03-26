package com.example.truckercore.view.fragments.phone_auth_code_verification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.truckercore.databinding.FragmentPhoneAuthCodeVerificationBinding
import com.example.truckercore.view.expressions.hideKeyboard
import com.ozcanalasalvar.otp_view.view.OtpView

class PhoneAuthCodeVerificationFragment : Fragment() {

    private var _binding: FragmentPhoneAuthCodeVerificationBinding? = null
    private val binding get() = _binding!!

    private var code = ""
    private val phoneNumber = "(62)98132-4562"

    //----------------------------------------------------------------------------------------------
    // onCreate()
    //----------------------------------------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    //----------------------------------------------------------------------------------------------
    // onCreateView()
    //----------------------------------------------------------------------------------------------
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhoneAuthCodeVerificationBinding.inflate(layoutInflater)
        return binding.root
    }

    //----------------------------------------------------------------------------------------------
    // onViewCreated
    //----------------------------------------------------------------------------------------------
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setMessageText()
        setMainLayoutClickListener()
        setOtpChangedListener()
        setButtonClickListener()
    }

    private fun setMessageText() {
        val message = "Você receberá um código de verificação " +
                "por mensagem de texto no número: $phoneNumber."
        binding.fragPhoneAuthCodeVerificationMessage.setText(message)
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
                    code = value
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
            fragPhoneAuthCodeVerificationOtp
        }
    }

    //----------------------------------------------------------------------------------------------
    // onDestroyView()
    //----------------------------------------------------------------------------------------------
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}