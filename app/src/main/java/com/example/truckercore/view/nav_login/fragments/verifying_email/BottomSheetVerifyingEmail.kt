package com.example.truckercore.view.nav_login.fragments.verifying_email

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.truckercore.databinding.LayoutBottomSheetVerifyingEmailBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetVerifyingEmail(
    private val onRetry: () -> Unit,
    private val onChangeEmail: () -> Unit
) : BottomSheetDialogFragment() {

    private var _binding: LayoutBottomSheetVerifyingEmailBinding? = null
    private val binding get() = _binding!!

    //----------------------------------------------------------------------------------------------
    // On Create View
    //----------------------------------------------------------------------------------------------
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LayoutBottomSheetVerifyingEmailBinding.inflate(layoutInflater)
        return binding.root
    }

    //----------------------------------------------------------------------------------------------
    // On View Created
    //----------------------------------------------------------------------------------------------
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setExpanded()
        setResendButtonClickListener()
        setRegisterAnotherEmailClickListener()
    }

    private fun setExpanded() {
        val behavior = BottomSheetBehavior.from(binding.fragVerifyingEmailBottomSheet)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun setRegisterAnotherEmailClickListener() {
        binding.bsVerifyingEmailResendButton.setOnClickListener {
            onRetry()
            buttonCLicked = true
            dismiss()
        }
    }

    private fun setResendButtonClickListener() {
        binding.bsVerifyingEmailAnotherEmailButton.setOnClickListener {
            onChangeEmail()
            buttonCLicked = true
            dismiss()
        }
    }

    //----------------------------------------------------------------------------------------------
    // On Dismiss
    //----------------------------------------------------------------------------------------------
    private var buttonCLicked = false
    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        if(!buttonCLicked) onRetry()
    }

    companion object {
        const val TAG = "BottomSheetVerifyingEmail"
    }

}
