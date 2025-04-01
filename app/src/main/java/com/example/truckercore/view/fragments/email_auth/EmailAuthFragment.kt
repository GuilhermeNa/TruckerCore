package com.example.truckercore.view.fragments.email_auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.truckercore.R
import com.example.truckercore.databinding.FragmentEmailAuthBinding
import com.example.truckercore.view.expressions.hideKeyboard
import com.example.truckercore.view.expressions.navigateTo
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthFragEvent.AlreadyHaveAccountButtonCLicked
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthFragEvent.CreateAccountButtonCLicked
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthFragState.Creating
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthFragState.Error
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthFragState.Initial
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthUiHandler
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class EmailAuthFragment : Fragment() {

    // Binding -------------------------------------------------------------------------------------
    private var _binding: FragmentEmailAuthBinding? = null
    private val binding get() = _binding!!
    private var uiHandler: EmailAuthUiHandler? = null

    // ViewModel -----------------------------------------------------------------------------------
    private val viewModel: EmailAuthViewModel by viewModel()

    //----------------------------------------------------------------------------------------------
    // onCreate()
    //----------------------------------------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            setFragmentStateManager()
            setFragmentEventsManager()
        }
    }

    private fun CoroutineScope.setFragmentStateManager() {
        launch {
            viewModel.fragmentState.collect { state ->
                when (state) {
                    is Initial -> {}
                    is Creating -> {}
                    is Error -> {}
                }
            }
        }
    }

    private suspend fun setFragmentEventsManager() {
        viewModel.event.collect { event ->
            when (event) {
                is CreateAccountButtonCLicked -> viewModel.setState(Creating)
                is AlreadyHaveAccountButtonCLicked -> navigateTo(
                    EmailAuthFragmentDirections.actionEmailAuthFragmentToLoginFragment()
                )
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
        _binding = FragmentEmailAuthBinding.inflate(layoutInflater)
        uiHandler = EmailAuthUiHandler(binding)
        return binding.root
    }

    //----------------------------------------------------------------------------------------------
    // onViewCreated()
    //----------------------------------------------------------------------------------------------
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRegisterButtonListener()
        setAlreadyRegisteredButtonListener()
        setMainLayoutClickListener()
        setNameFieldManager()
        setSurnameEditTextFocusListener()
        setPasswordEditTextFocusListener()

        binding.fragEmailAuthNameEditText.setOnClickListener {
            binding.fragEmailAuthNameError.text = "Testando o text ode erro"
            val motionLayout = binding.fragEmailAuthMain
            motionLayout.setTransitionDuration(100)
            motionLayout.transitionToState(R.id.frag_email_auth_state_1)
        }
        binding.fragEmailAuthSurnameEditText.setOnClickListener {
            val motionLayout = binding.fragEmailAuthMain
            motionLayout.setTransitionDuration(100)
            motionLayout.transitionToState(R.id.frag_email_auth_state_2)
        }
        binding.fragEmailAuthEmailEditText.setOnClickListener {
            val motionLayout = binding.fragEmailAuthMain
            motionLayout.setTransitionDuration(100)
            motionLayout.transitionToState(R.id.frag_email_auth_state_3)
        }
        binding.fragEmailAuthPasswordEditText.setOnClickListener {
            val motionLayout = binding.fragEmailAuthMain
            motionLayout.setTransitionDuration(100)
            motionLayout.transitionToState(R.id.frag_email_auth_state_4)
        }
        binding.fragEmailAuthConfirmPasswordEditText.setOnClickListener {
            val motionLayout = binding.fragEmailAuthMain
            motionLayout.setTransitionDuration(100)
            motionLayout.transitionToState(R.id.frag_email_auth_state_5)
        }
    }

    private fun setMainLayoutClickListener() {
        binding.fragEmailAuthMain.setOnClickListener {
            hideKeyboard()
            uiHandler?.clearLayoutFocus()
        }
    }

    private fun setNameFieldManager() {
        binding.fragEmailAuthNameEditText.setOnFocusChangeListener { _, hasFocus ->
           // uiHandler?.setNameHelper(selected = hasFocus)
        }

        binding.fragEmailAuthNameEditText.addTextChangedListener { text ->
   /*         val wrongFormat = !text.toString().isNameFormat()
            val isEmpty = text.toString().isEmpty()

            when {
                isEmpty -> uiHandler?.setNameError(false)
                wrongFormat -> uiHandler?.setNameError(true)
                else -> uiHandler?.setNameError(false)
            }*/

        }
    }

    private fun setSurnameEditTextFocusListener() {
        binding.fragEmailAuthSurnameEditText.setOnFocusChangeListener { _, hasFocus ->
          //  uiHandler?.setSurnameHelper(hasFocus)
        }
    }



    private fun setPasswordEditTextFocusListener() {
        binding.fragEmailAuthPasswordEditText.setOnFocusChangeListener { _, hasFocus ->
          //  uiHandler?.setPasswordHelper(selected = hasFocus)
        }
    }


    private fun setRegisterButtonListener() {
        binding.fragEmailAuthRegisterButton.setOnClickListener {
            viewModel.setEvent(CreateAccountButtonCLicked)
        }
    }

    private fun setAlreadyRegisteredButtonListener() {
        binding.fragEmailAuthAlreadyRegisteredButton.setOnClickListener {
            viewModel.setEvent(AlreadyHaveAccountButtonCLicked)
        }
    }

    //----------------------------------------------------------------------------------------------
    // onViewDestroyed()
    //----------------------------------------------------------------------------------------------
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}