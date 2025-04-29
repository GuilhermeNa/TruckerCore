package com.example.truckercore.business_admin.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.truckercore.R
import com.example.truckercore.business_admin.view_model.view_models.BaSplashFragmentViewModel
import com.example.truckercore.databinding.FragmentSplashBinding
import com.example.truckercore.view.activities.NotificationActivity
import com.example.truckercore.view.expressions.animPumpAndDump
import com.example.truckercore.view.expressions.getFlavor
import com.example.truckercore.view.expressions.navigateTo
import com.example.truckercore.view_model.states.SplashFragState
import com.example.truckercore.view_model.states.SplashFragState.Error
import com.example.truckercore.view_model.states.SplashFragState.FirstAccess
import com.example.truckercore.view_model.states.SplashFragState.Initial
import com.example.truckercore.view_model.states.SplashFragState.UserLoggedIn
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class BaSplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BaSplashFragmentViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.fragmentState.collect { state ->
                    if (state != Initial) removeLoadingBar()
                    when (state) {
                        is Initial -> handleInitialState()
                        is FirstAccess -> handleFirstAccess()
                        is UserLoggedIn -> handleLoggedUser(state)
                        is SplashFragState.UserNotFound -> handleUserNotFound()
                        is Error -> handleError(state.error)
                    }
                }
            }
        }
    }

    private fun handleInitialState() {
        setMotionLayoutCompletedListener {
            viewModel.run()
        }
    }

    private fun handleFirstAccess() {
        val flavor = requireContext().getFlavor()
        val direction = BaSplashFragmentDirections.actionSplashFragmentToWelcomeFragment(flavor)
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

    //----------------------------------------------------------------------------------------------
    // On Create View
    //----------------------------------------------------------------------------------------------
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
    }

    private fun setMotionLayoutCompletedListener(complete: () -> Unit) {
        binding.motionLayout.setTransitionListener(
            object : MotionLayout.TransitionListener {
                override fun onTransitionStarted(
                    motionLayout: MotionLayout?,
                    startId: Int,
                    endId: Int
                ) {
                }

                override fun onTransitionChange(
                    motionLayout: MotionLayout?,
                    startId: Int,
                    endId: Int,
                    progress: Float
                ) {
                }

                override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                    complete()
                }

                override fun onTransitionTrigger(
                    motionLayout: MotionLayout?,
                    triggerId: Int,
                    positive: Boolean,
                    progress: Float
                ) {
                }

            }
        )
    }

    private suspend fun removeLoadingBar() {
        binding.fragSplashProgressbar.animPumpAndDump()
        delay(600)
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