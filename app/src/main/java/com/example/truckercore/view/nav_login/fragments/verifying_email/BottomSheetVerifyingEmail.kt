package com.example.truckercore.view.nav_login.fragments.verifying_email

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.truckercore.databinding.LayoutBottomSheetVerifyingEmailBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetVerifyingEmail : BottomSheetDialogFragment() {

    private var _binding: LayoutBottomSheetVerifyingEmailBinding? = null
    private val binding get() = _binding!!

    private var buttonCLicked = false

    private var listener: BottomSheetVerifyingEmailListener? = null

    //----------------------------------------------------------------------------------------------
    // On Create View
    //----------------------------------------------------------------------------------------------
    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = parentFragment as? BottomSheetVerifyingEmailListener
            ?: throw IllegalStateException("Parent fragment must implement BottomSheetVerifyingEmailListener")
    }

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
        // Parent View Behavior
        val parent = view?.parent as? View
        parent?.let {
            val behavior = BottomSheetBehavior.from(it)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        // BottomSheet Behavior
        val behavior = BottomSheetBehavior.from(binding.fragVerifyingEmailBottomSheet)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun setResendButtonClickListener() {
        binding.bsVerifyingEmailAnotherEmailButton.setOnClickListener {
            listener?.onChangeEmail()
            buttonCLicked = true
            dismiss()
        }
    }

    private fun setRegisterAnotherEmailClickListener() {
        binding.bsVerifyingEmailResendButton.setOnClickListener {
            listener?.onRetry()
            buttonCLicked = true
            dismiss()
        }
    }

    //----------------------------------------------------------------------------------------------
    // On Dismiss
    //----------------------------------------------------------------------------------------------
    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        if (!buttonCLicked) listener?.onRetry()
    }

    //----------------------------------------------------------------------------------------------
    // On Destroy View
    //----------------------------------------------------------------------------------------------
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        listener = null
    }

    companion object {
        const val TAG = "BottomSheetVerifyingEmail"
    }

    fun alreadyShown(): Boolean = try {
        parentFragmentManager.findFragmentByTag(TAG) != null
    } catch (_: Exception) {
        false
    }


}
