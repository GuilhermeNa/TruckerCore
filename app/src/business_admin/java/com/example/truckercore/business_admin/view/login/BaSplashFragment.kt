package com.example.truckercore.business_admin.view.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.truckercore.R
import com.example.truckercore.business_admin.view_model.login.BaSplashFragmentViewModel
import com.example.truckercore.databinding.FragmentSplashBinding
import com.example.truckercore.view.activities.NotificationActivity
import com.example.truckercore.view.expressions.animPumpAndDump
import com.example.truckercore.view.expressions.collectOnStarted
import com.example.truckercore.view.expressions.navigateTo
import com.example.truckercore.view_model.states.SplashFragState
import com.example.truckercore.view_model.states.SplashFragState.Error
import com.example.truckercore.view_model.states.SplashFragState.FirstAccess
import com.example.truckercore.view_model.states.SplashFragState.Initial
import com.example.truckercore.view_model.states.SplashFragState.UserLoggedIn
import kotlinx.coroutines.delay
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val MOTION_DELAY = 3000L

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
        collectOnStarted(flow = viewModel.fragmentState, timer = MOTION_DELAY) { state ->
            if (state != Initial) removeProgressBar()
            when (state) {
                is Initial -> runViewModel()
                is FirstAccess -> handleFirstAccess()
                is UserLoggedIn -> handleLoggedUser(state)
                is SplashFragState.UserNotFound -> handleUserNotFound()
                is Error -> handleError(state.error)
            }
        }
    }

    private suspend fun removeProgressBar() {
        binding.fragSplashProgressbar.animPumpAndDump()
        delay(550)
    }

    private fun runViewModel() {
        viewModel.run()
    }

    private fun handleFirstAccess() {
        val direction = BaSplashFragmentDirections.actionSplashFragmentToWelcomeFragment()
        navigateTo(direction)
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
        navigateToNotificationActivityWithAnError(error)
    }

    private fun navigateToNotificationActivityWithAnError(error: Exception) {
        val intent = NotificationActivity.newInstance(
            context = requireContext(),
            gifRes = R.drawable.gif_error,
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