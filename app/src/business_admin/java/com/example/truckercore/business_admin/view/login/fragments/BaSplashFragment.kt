package com.example.truckercore.business_admin.view.login.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.truckercore.business_admin.view_model.login.fragments.BaSplashFragmentViewModel
import com.example.truckercore.databinding.FragmentSplashBinding
import com.example.truckercore.view.activities.ErrorActivity
import com.example.truckercore.view.expressions.collectOnStarted
import com.example.truckercore.view_model.SplashFragState.Error
import com.example.truckercore.view_model.SplashFragState.FirstAccess
import com.example.truckercore.view_model.SplashFragState.Initial
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
        collectOnStarted(viewModel.fragmentState) { state ->
            when (state) {
                is Initial -> initializeViewModelFlow()
                is FirstAccess -> handleFirstAccess()
                is UserLoggedIn -> handleLoggedUser(state)
                is UserNotFound -> handleUserNotFound()
                is Error -> handleError(state.error)
            }
        }
    }

    private fun initializeViewModelFlow() {
        viewModel.initialize()
    }

    private fun handleFirstAccess() {
        navigateToWelcomeFragment()
    }

    private fun handleLoggedUser(state: UserLoggedIn) {
        when (state) {
            is UserLoggedIn.SystemAccessAllowed -> navigateToMainActivity()
            is UserLoggedIn.SystemAccessDenied -> navigateToDeniedSystemAccessFragment()
            is UserLoggedIn.ProfileIncomplete -> navigateToProfileCreationFragment()
        }
    }

    private fun handleUserNotFound() {
        navigateToLoginFragment()
    }

    private fun handleError(error: Exception) {
        navigateToErrorActivity(error)
    }

    private fun navigateToErrorActivity(error: Exception) {
        val intent = ErrorActivity.newInstance(
            context = requireContext(),
            errorHeader = viewModel.getErrorTitle(),
            errorBody = viewModel.getErrorMessage()
        )
        startActivity(intent)
        requireActivity().finish()
    }

    private fun navigateToWelcomeFragment() {}

    private fun navigateToLoginFragment() {}

    private fun navigateToProfileCreationFragment() {}

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