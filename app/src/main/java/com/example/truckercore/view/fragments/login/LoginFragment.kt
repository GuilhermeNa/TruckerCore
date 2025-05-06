package com.example.truckercore.view.fragments.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.truckercore.databinding.FragmentLoginBinding
import com.example.truckercore.view.fragments._base.CloseAppFragment
import com.example.truckercore.view_model.view_models.login.LoginEvent
import com.example.truckercore.view_model.view_models.login.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : CloseAppFragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private var _stateHandler: LoginUiStateHandler? = null
    private val stateHandler get() = _stateHandler!!

    private val viewModel: LoginViewModel by viewModel()

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
        _stateHandler = LoginUiStateHandler(binding)
        return binding.root
    }

    //----------------------------------------------------------------------------------------------
    // onViewCreated
    //----------------------------------------------------------------------------------------------
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBackgroundViewsClickListener()
        setEnterButtonClickListener()
        setNewAccountButtonClickListener()
        setForgetPasswordButtonClickListener()
    }

    private fun setBackgroundViewsClickListener() {
        binding.fragLoginBackground.setOnClickListener {
            viewModel.onEvent(LoginEvent.UiEvent.BackGroundCLick)
        }
        binding.fragLoginCard.setOnClickListener {
            viewModel.onEvent(LoginEvent.UiEvent.BackGroundCLick)
        }
    }

    private fun setEnterButtonClickListener() {
        binding.fragLoginEnterButton.setOnClickListener {
            viewModel.onEvent(LoginEvent.UiEvent.EnterButtonClick)
        }
    }

    private fun setNewAccountButtonClickListener() {
        binding.fragLoginNewAccountButton.setOnClickListener {
            viewModel.onEvent(LoginEvent.UiEvent.NewAccountButtonClick)
        }
    }

    private fun setForgetPasswordButtonClickListener() {
        binding.fragLoginForgetPasswordButton.setOnClickListener {
            viewModel.onEvent(LoginEvent.UiEvent.ForgetPasswordButtonClick)
        }
    }

    //----------------------------------------------------------------------------------------------
    // onDestroyView
    //----------------------------------------------------------------------------------------------
    override fun onDestroyView() {
        super.onDestroyView()
        _stateHandler = null
        _binding = null
    }

}