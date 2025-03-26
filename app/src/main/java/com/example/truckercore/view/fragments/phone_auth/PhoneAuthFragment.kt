package com.example.truckercore.view.fragments.phone_auth

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.truckercore.databinding.FragmentPhoneAuthBinding
import com.example.truckercore.view.expressions.hideKeyboard
import com.example.truckercore.view.expressions.navigateTo
import com.example.truckercore.view_model.view_models.phone_auth.PhoneAuthActivityViewModel

class PhoneAuthFragment : Fragment() {

    // Binding -------------------------------------------------------------------------------------
    private var _binding: FragmentPhoneAuthBinding? = null
    private val binding get() = _binding!!

    private val activityVm: PhoneAuthActivityViewModel by activityViewModels()

    //----------------------------------------------------------------------------------------------
    // onCreateView()
    //----------------------------------------------------------------------------------------------
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhoneAuthBinding.inflate(layoutInflater)
        return binding.root
    }

    //----------------------------------------------------------------------------------------------
    // onViewCreated()
    //----------------------------------------------------------------------------------------------
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setEditTextConfig()
        setMainLayoutClickListener()
        setPhoneValidityListener()
        setButtonListener()
        activityVm.teste
    }

    private fun setEditTextConfig() {
        // Link ccp to editText
        val ccp = binding.fragPhoneAuthCcp
        val editText = binding.fragPhoneAuthEditText
        ccp.registerCarrierNumberEditText(editText)

        // Config clear focus
        editText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                hideKeyboardAndClearTxtViewFocus()
                true // Indicate that the action was handled
            } else {
                false // Indicate that the action was not handled
            }
        }
    }

    private fun setMainLayoutClickListener() {
        binding.fragPhoneAuthMain.setOnClickListener {
            hideKeyboardAndClearTxtViewFocus()
        }
    }

    private fun setPhoneValidityListener(): Unit = with(binding) {
        fragPhoneAuthCcp.setPhoneNumberValidityChangeListener { isValid ->
            binding.fragPhoneAuthButton.isEnabled = isValid
            setEditTextColor(isValid)
        }
    }

    private fun setEditTextColor(isValid: Boolean): Unit = with(binding.fragPhoneAuthEditText) {
        if (isValid) setTextColor(Color.BLACK)
        else setTextColor(Color.RED)
    }

    private fun setButtonListener(): Unit = with(binding) {
        fragPhoneAuthButton.setOnClickListener {
            hideKeyboardAndClearTxtViewFocus()
            val phoneNumber = fragPhoneAuthCcp.fullNumberWithPlus
            //setEvent(SendCodeButtonCLicked(phoneNumber))

            //Navigate
            val direction = PhoneAuthFragmentDirections
                .actionPhoneAuthFragment2ToPhoneAuthCodeVerificationFragment()
            navigateTo(direction)
        }
    }

    private fun hideKeyboardAndClearTxtViewFocus() {
        hideKeyboard()
        binding.fragPhoneAuthEditText.clearFocus()
    }

    //----------------------------------------------------------------------------------------------
    // onViewDestroyed()
    //----------------------------------------------------------------------------------------------
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}