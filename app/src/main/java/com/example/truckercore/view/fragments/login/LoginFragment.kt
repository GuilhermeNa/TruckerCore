package com.example.truckercore.view.fragments.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.truckercore.databinding.FragmentLoginBinding
import com.example.truckercore.view.fragments._base.CloseAppFragment

class LoginFragment : CloseAppFragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    //----------------------------------------------------------------------------------------------
    // OnCreate
    //----------------------------------------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    //----------------------------------------------------------------------------------------------
    // onCreateView
    //----------------------------------------------------------------------------------------------
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    //----------------------------------------------------------------------------------------------
    // onViewCreated
    //----------------------------------------------------------------------------------------------
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fragLoginEmailLayout.error = "Teste erro"
        setNewAccountButtonClickListener()
        setForgetPasswordButtonClickListener()
    }

    private fun setNewAccountButtonClickListener() {
        binding.fragLoginNewAccountButton.setOnClickListener {
            binding.fragLoginEnterButton.isEnabled = false
        }
    }

    private fun setForgetPasswordButtonClickListener() {
        binding.fragLoginForgetPasswordButton.setOnClickListener {
            binding.fragLoginEnterButton.isEnabled = true
        }
    }

    //----------------------------------------------------------------------------------------------
    // onDestroyView
    //----------------------------------------------------------------------------------------------
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}