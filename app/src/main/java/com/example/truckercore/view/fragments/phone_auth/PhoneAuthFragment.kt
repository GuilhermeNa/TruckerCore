package com.example.truckercore.view.fragments.phone_auth

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.navGraphViewModels
import com.example.truckercore.R
import com.example.truckercore.databinding.FragmentPhoneAuthBinding
import com.example.truckercore.view.expressions.hideKeyboard
import com.example.truckercore.view.expressions.navigateTo
import com.example.truckercore.view.expressions.showSnackBarRed
import com.example.truckercore.view_model.view_models.phone_auth.PhoneAuthFragState
import com.example.truckercore.view_model.view_models.phone_auth.PhoneAuthFragState.Error
import com.example.truckercore.view_model.view_models.phone_auth.PhoneAuthFragState.Initial
import com.example.truckercore.view_model.view_models.phone_auth.PhoneAuthFragState.SelVerification
import com.example.truckercore.view_model.view_models.phone_auth.PhoneAuthFragState.Success
import com.example.truckercore.view_model.view_models.phone_auth.PhoneAuthFragState.UserRequestedCode
import com.example.truckercore.view_model.view_models.phone_auth.PhoneAuthFragState.UserVerification
import com.example.truckercore.view_model.view_models.phone_auth.PhoneAuthFragmentViewModel
import com.example.truckercore.view_model.view_models.phone_auth.PhoneAuthSharedViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class PhoneAuthFragment : Fragment() {

    // Binding -------------------------------------------------------------------------------------
    private var _binding: FragmentPhoneAuthBinding? = null
    private val binding get() = _binding!!

    private val fragViewModel: PhoneAuthFragmentViewModel by viewModel()
    private val sharedViewModel: PhoneAuthSharedViewModel by navGraphViewModels(R.id.nav_graph_login)

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            val newState = SelVerification(credential)
            fragViewModel.setState(newState)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            val cause = when (e) {
                is FirebaseNetworkException -> Error(
                    message = "Falha de conexão ao requisitar código. Tente novamente em breve.",
                    type = PhoneAuthFragState.PhoneAuthFragError.Network
                )

                is FirebaseTooManyRequestsException -> Error(
                    message = "A cota para envios de mensagens de verificação foi atingida.",
                    type = PhoneAuthFragState.PhoneAuthFragError.RequestLimit
                )

                else -> Error(
                    message = "Erro desconhecido ao requisitar código.",
                    type = PhoneAuthFragState.PhoneAuthFragError.Unknown
                )
            }

            fragViewModel.setState(cause)
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            sharedViewModel.storeVerificationId(verificationId)
            fragViewModel.setState(UserVerification)
        }

    }

    //----------------------------------------------------------------------------------------------
    // onCreate()
    //----------------------------------------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                fragViewModel.fragmentState.collect { state ->
                    when (state) {
                        is Initial -> Unit
                        is UserRequestedCode -> handleUserRequestedCode(state.phoneNumber)
                        is SelVerification -> fragViewModel.authenticateUser(state.credential)
                        is UserVerification -> navigateToVerificationFragment()
                        is Success -> TODO()
                        is Error -> showSnackBarRed(state.message)
                    }
                }
            }
        }
    }

    private fun handleUserRequestedCode(phoneNumber: String) {
        showLoadingDialog()
        requestCode(phoneNumber)
    }

    private fun showLoadingDialog() {
        AlertDialog.Builder(requireContext())
            .setIcon(R.drawable.icon_progress)
            .setTitle("Solicitando mensagem")
            .setMessage("Sua solicitação está sendo processada, aguarde um instante.")
            .setCancelable(false)
            .create()
            .show()
    }

    private fun requestCode(phoneNumber: String) {
        val auth = FirebaseAuth.getInstance().apply {
            useAppLanguage()
        }
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(requireActivity())
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun navigateToVerificationFragment() {

    }

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

    private fun hideKeyboardAndClearTxtViewFocus() {
        hideKeyboard()
        binding.fragPhoneAuthEditText.clearFocus()
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

    private fun setButtonListener() {
        binding.fragPhoneAuthButton.setOnClickListener {
            hideKeyboardAndClearTxtViewFocus()
            triggerRequestCodeEvent()
        }
    }

    private fun triggerRequestCodeEvent() {
        val phoneNumber = binding.fragPhoneAuthCcp.fullNumberWithPlus
        fragViewModel.setState(UserRequestedCode(phoneNumber))
    }

    //----------------------------------------------------------------------------------------------
    // onViewDestroyed()
    //----------------------------------------------------------------------------------------------
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}