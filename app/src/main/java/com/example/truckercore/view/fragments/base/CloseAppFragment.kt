package com.example.truckercore.view.fragments.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.example.truckercore.view.expressions.showToast
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val PRESS_AGAIN_TO_EXIT = "Pression novamente para sair"

/**
 * Abstract [Fragment] that handles the back press event to prompt the user for confirmation
 * before exiting the app. This fragment should be used as a base class for fragments where
 * pressing the back button should trigger a confirmation dialog or message before closing
 * the application.
 *
 * The user is shown a toast message, asking them to press back again to confirm exit. If the
 * user confirms, the app will close.
 */
abstract class CloseAppFragment : Fragment() {

    /**
     * Lazy-initialized instance of [ExitAppManager] to handle the logic of app exit confirmation.
     * Manages the decision to exit the app after back press events.
     */
    private val exitManager by lazy { ExitAppManager() }

    /**
     * Lazy-initialized [OnBackPressedCallback] that handles the back press event in the fragment.
     * This callback shows a toast asking the user to press back again to exit if the exit has not
     * been confirmed. If the exit is confirmed, the activity will be finished (app closed).
     */
    private val backPressCallback by lazy {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val canExit = exitManager.handleExitAttempt()

                if (canExit) activity?.finish()
                else showToast(PRESS_AGAIN_TO_EXIT, Toast.LENGTH_LONG)

            }
        }
    }

    //----------------------------------------------------------------------------------------------
    // onViewCreated()
    //----------------------------------------------------------------------------------------------
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBackPressedCallback()
    }

    /**
     * Sets a callback to handle the back press event in the fragment's lifecycle.
     *
     * This method adds a custom back press callback to the `OnBackPressedDispatcher` of the
     * current activity, ensuring that the back press event is intercepted and handled by
     * the provided `backPressCallback`. The callback is associated with the `viewLifecycleOwner`,
     * meaning it will be automatically removed when the fragment's view is destroyed.
     */
    private fun setBackPressedCallback() {
        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, backPressCallback)
    }

    //----------------------------------------------------------------------------------------------
    // onDestroyView()
    //----------------------------------------------------------------------------------------------
    override fun onDestroyView() {
        super.onDestroyView()
        exitManager.cancelCoroutines()
    }

}

// Default timer value in milliseconds (5 seconds)
private const val DEFAULT_TIMER = 5000L

/**
 *  Class responsible for managing the exit logic of the app
 */
private class ExitAppManager(
    private val resetTimer: Long = DEFAULT_TIMER,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {

    // Flag to track whether the app can be closed
    private var canCloseApp = false

    // Job used to handle the timer for resetting the canCloseApp flag
    private var counterJob: Job? = null

    //----------------------------------------------------------------------------------------------

    /**
     * Handles an exit attempt.
     * Returns true if the app can be closed, false otherwise.
     */
    fun handleExitAttempt(): Boolean {
        // If the app can be closed, allow exit
        if (canCloseApp) return true
        else {
            // Allow exit after starting the timer
            allowAppToClose(true)
            launchCounterJob()
            return false
        }
    }

    /**
     * Starts the counter job to reset the [canCloseApp] flag after a delay.
     */
    private fun launchCounterJob() {
        // Launch the job only if there isn't an active one
        if (canLaunchJob())
            counterJob = CoroutineScope(dispatcher).launch {
                delay(resetTimer) // Wait for the configured delay time
                allowAppToClose(false) // Reset the flag to false after the delay
            }
    }

    /**
     * Determines if a new job can be launched based on the current job's status.
     * Returns true if no job is currently running or if the existing job has completed.
     */
    private fun canLaunchJob() = counterJob == null || counterJob?.isCompleted == true

    /**
     * Sets the value of the [canCloseApp] flag.
     * @param allow Boolean value to set whether the app can be closed or not.
     */
    private fun allowAppToClose(allow: Boolean) {
        canCloseApp = allow
    }

    /**
     * Cancels the currently running counter job, if it exists. Set [canCloseApp] flag to false.
     */
    fun cancelCoroutines() {
        allowAppToClose(false)
        counterJob?.cancel()
    }

}