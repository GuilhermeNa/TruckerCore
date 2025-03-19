package com.example.truckercore.business_admin.view.login.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.truckercore.business_admin.view_model.login.fragments.BaSplashFragmentViewModel
import com.example.truckercore.databinding.FragmentSplashBinding
import com.example.truckercore.view.expressions.repeatOnFragmentStart
import com.example.truckercore.view_model.SplashFragState.Initial
import com.example.truckercore.view_model.SplashFragState.SystemAccess
import com.example.truckercore.view_model.SplashFragState.UserLoggedIn
import com.example.truckercore.view_model.SplashFragState.UserNotFound
import org.koin.androidx.viewmodel.ext.android.viewModel

class BaSplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BaSplashFragmentViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(layoutInflater)
        return binding.root
    }

    //----------------------------------------------------------------------------------------------
    // On View Created
    //----------------------------------------------------------------------------------------------

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.repeatOnFragmentStart(this) {
            viewModel.fragState.collect { state ->
                when (state) {
                    is Initial -> searchForLoggedUser()
                    is UserLoggedIn -> handleLoggedUser(state)
                    is UserNotFound -> handleUserNotFound(state)
                    is SystemAccess -> handleSystemAccess(state)
                }
            }
        }
    }

    private fun handleLoggedUser(state: UserLoggedIn) {
        when (state) {
            is UserLoggedIn.ProfileComplete -> tryAccessSystem()
            is UserLoggedIn.ProfileIncomplete -> navigateToProfileCreationFragment()
        }
    }

    private fun handleUserNotFound(state: UserNotFound) {
        when (state) {
            is UserNotFound.FirstAccess -> navigateToWelcomeFragment()
            is UserNotFound.NotFirstAccess -> navigateToLoginFragment()
        }
    }

    private fun handleSystemAccess(state: SystemAccess) {
        when (state) {
            is SystemAccess.AccessAllowed -> navigateToMainActivity()
            is SystemAccess.AccessDenied -> navigateToDeniedSystemAccessFragment()
        }
    }

    private fun searchForLoggedUser() {
        viewModel.searchForLoggedUser()
    }

    private fun navigateToWelcomeFragment() {}

    private fun navigateToLoginFragment() {}

    private fun navigateToProfileCreationFragment() {}

    private fun tryAccessSystem() {

    }

    private fun navigateToMainActivity() {

    }

    private fun navigateToDeniedSystemAccessFragment() {

    }

    //----------------------------------------------------------------------------------------------
    // On Destroy View
    //----------------------------------------------------------------------------------------------

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}