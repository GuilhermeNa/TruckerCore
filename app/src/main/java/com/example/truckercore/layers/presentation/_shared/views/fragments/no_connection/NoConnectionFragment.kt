package com.example.truckercore.layers.presentation._shared.views.fragments.no_connection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.LifecycleOwner
import com.example.truckercore.core.expressions.popBackStack
import com.example.truckercore.core.expressions.showToast
import com.example.truckercore.databinding.FragmentNoConnectionBinding
import com.example.truckercore.presentation._shared._base.fragments.CloseAppFragment
import com.example.truckercore.presentation._shared.expressions.isInternetAvailable
import com.example.truckercore.presentation._shared.expressions.launchOnFragmentLifecycle
import kotlinx.coroutines.delay

/**
 * Fragment that handles cases where the device is offline.
 * It provides the user an option to retry connecting to the internet.
 * On successful reconnection, the fragment returns a result to the calling fragment.
 */
class NoConnectionFragment : CloseAppFragment() {

    // ViewBinding instance for accessing layout views safely.
    private var _binding: FragmentNoConnectionBinding? = null
    private val binding get() = _binding!!

    // Handles UI transitions and state management.
    private val stateHandler by lazy { NoConnectionStateHandler() }

    //----------------------------------------------------------------------------------------------
    // Lifecycle: onCreateView
    //----------------------------------------------------------------------------------------------

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoConnectionBinding.inflate(inflater)
        stateHandler.initialize(binding) // Setup binding for state handler
        return binding.root
    }

    //----------------------------------------------------------------------------------------------
    // Lifecycle: onViewCreated
    //----------------------------------------------------------------------------------------------

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setButtonClickListener()
    }

    /**
     * Sets up click listener for the retry button.
     * Disables the button during the connection check, and handles the result accordingly.
     */
    private fun setButtonClickListener() {
        binding.fragNoConnectionButton.setOnClickListener {
            stateHandler.checking() // Switch to loading state

            // Launch coroutine tied to fragment lifecycle
            launchOnFragmentLifecycle {
                delay(ANIMATION_DELAY)

                if (requireContext().isInternetAvailable()) {
                    closeFragmentAndNotifyListener()
                } else showSnackAndReturnToIdleState()
            }
        }
    }

    /**
     * Called when internet is reconnected.
     * Pops this fragment and returns a result to the caller.
     */
    private fun closeFragmentAndNotifyListener() {
        popBackStack()
        setFragmentResult(REQUEST_KEY, bundleOf(BUNDLE_KEY to true))
    }

    /**
     * Called when reconnection fails.
     * Displays feedback to the user and resets the UI state.
     */
    private fun showSnackAndReturnToIdleState() {
        showToast(TOAST_MESSAGE)
        stateHandler.idle()
    }

    //----------------------------------------------------------------------------------------------
    // Lifecycle: onDestroyView
    //----------------------------------------------------------------------------------------------

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //----------------------------------------------------------------------------------------------
    // Companion Object (Constants & Listener Setup)
    //----------------------------------------------------------------------------------------------

    companion object {
        private const val ANIMATION_DELAY = 3000L
        private const val TOAST_MESSAGE = "Failed to connect"
        private const val REQUEST_KEY = "no_connection_result"
        private const val BUNDLE_KEY = "reconnected"

        /**
         * Registers a listener for receiving the reconnection result.
         *
         * @param fragmentManager The FragmentManager used to register the listener.
         * @param lifecycleOwner The LifecycleOwner to observe the result within.
         * @param onResult Callback invoked with `true` if reconnected successfully.
         */
        fun setResultListener(
            fragmentManager: FragmentManager,
            lifecycleOwner: LifecycleOwner,
            onResult: (Boolean) -> Unit
        ) {
            fragmentManager.setFragmentResultListener(REQUEST_KEY, lifecycleOwner) { _, bundle ->
                val result = bundle.getBoolean(BUNDLE_KEY, false)
                onResult(result)
            }
        }
    }
}