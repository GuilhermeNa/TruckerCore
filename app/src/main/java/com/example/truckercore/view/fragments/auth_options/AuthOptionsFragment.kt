package com.example.truckercore.view.fragments.auth_options

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.truckercore.R
import com.example.truckercore.databinding.FragmentAuthOptionsBinding
import com.example.truckercore.view.expressions.loadGif
import com.example.truckercore.view.expressions.navigateTo

/**
 * A fragment that displays authentication options to the user, including options to log in via email or phone number.
 *
 * This fragment provides the user with options for logging into the app and supports navigation to different
 * authentication methods such as phone or email.
 *
 */
class AuthOptionsFragment : Fragment() {

    // Binding --------------------------------------------------------------
    private var _binding: FragmentAuthOptionsBinding? = null
    private val binding get() = _binding!!

    //----------------------------------------------------------------------------------------------
    // onCreateView()
    //----------------------------------------------------------------------------------------------
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthOptionsBinding.inflate(layoutInflater)
        return binding.root
    }

    //----------------------------------------------------------------------------------------------
    // onViewCreated()
    //----------------------------------------------------------------------------------------------
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindGif()
        setPhoneFabListener()
        setEmailFabListener()
        setAccountExistentButtonListener()
    }

    /**
     * Binds a GIF image to the fragment's image view.
     */
    private fun bindGif() {
        binding.fragAuthOptionsImage.loadGif(R.drawable.gif_locked, requireContext())
    }

    /**
     * Sets the listener for the "Phone FAB" button.
     * The button, when clicked, should navigate to the authentication screen for phone number-based login.
     */
    private fun setPhoneFabListener() {
        binding.fragAuthOptionsFabPhone.setOnClickListener {
            val direction = AuthOptionsFragmentDirections
                .actionAuthOptionsFragmentToPhoneAuthFragment()
            navigateTo(direction)
        }
    }

    /**
     * Sets the listener for the "Email FAB" button.
     * The button, when clicked, should navigate to the authentication screen for email-based login.
     */
    private fun setEmailFabListener() {
        binding.fragAuthOptionsFabEmail.setOnClickListener {
            val direction = AuthOptionsFragmentDirections
                .actionAuthOptionsFragmentToEmailAuthFragment()
            navigateTo(direction)
        }
    }

    /**
     * Sets the listener for the "Account Existent" button.
     * The button, when clicked, should navigate to the login screen.
     */
    private fun setAccountExistentButtonListener() {
        binding.fragAuthOptionsButton.setOnClickListener {
            TODO("Not yet implemented")
        }
    }

}