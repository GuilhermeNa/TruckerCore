package com.example.truckercore.view.helpers

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// Default timer value in milliseconds (5 seconds)
private const val DEFAULT_TIMER = 5000L

/**
 *  Class responsible for managing the exit logic of the app
 */
class ExitAppManager(
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