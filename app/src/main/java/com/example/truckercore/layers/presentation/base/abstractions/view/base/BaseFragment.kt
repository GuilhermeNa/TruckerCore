package com.example.truckercore.layers.presentation.base.abstractions.view.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.example.truckercore.core.my_lib.expressions.getTag
import com.example.truckercore.infra.logger.AppLogger
import com.example.truckercore.layers.presentation.base.contracts.BaseNavigator
import com.example.truckercore.layers.presentation.base.abstractions.view.base.InstanceStateHandler.markAsCreated

/**
 * Base class for all fragments in the application.
 *
 * This fragment provides:
 * - Centralized lifecycle logging
 * - Common navigation support via [BaseNavigator]
 * - Utility helpers to distinguish between initialization, recreation,
 *   and resumed UI states
 *
 * All application fragments should inherit from this class to ensure
 * consistent behavior and logging.
 */
abstract class BaseFragment : Fragment(), BaseNavigator {

    /**
     * Called when the fragment is first created.
     * Logs the lifecycle event.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppLogger.d(getTag, LIFECYCLE_CREATE)
    }

    /**
     * Called after the fragment's view has been created.
     * Logs the lifecycle event.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AppLogger.d(getTag, LIFECYCLE_VIEW_CREATED)
    }

    /**
     * Called when the fragment becomes visible.
     * Logs the lifecycle event.
     */
    override fun onStart() {
        super.onStart()
        AppLogger.d(getTag, LIFECYCLE_START)
    }

    /**
     * Called when the fragment is ready for user interaction.
     * Logs the lifecycle event.
     */
    override fun onResume() {
        super.onResume()
        AppLogger.d(getTag, LIFECYCLE_RESUME)
    }

    /**
     * Called when the fragment's view is about to be destroyed.
     * Logs the lifecycle event.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        AppLogger.d(getTag, LIFECYCLE_DESTROY_VIEW)
    }

    /**
     * Called to save the fragment's current state.
     * Marks the state as already created and logs the lifecycle event.
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.markAsCreated()
        AppLogger.d(getTag, LIFECYCLE_SAVE_INSTANCE_STATE)
    }

    /**
     * Called when the fragment is being destroyed.
     * Logs the lifecycle event.
     */
    override fun onDestroy() {
        super.onDestroy()
        AppLogger.d(getTag, LIFECYCLE_DESTROY)
    }

    /**
     * Lifecycle log message constants.
     */
    private companion object {
        private const val LIFECYCLE_CREATE = "Fragment Lifecycle: ON_CREATE"
        private const val LIFECYCLE_VIEW_CREATED = "Fragment Lifecycle: ON_VIEW_CREATED"
        private const val LIFECYCLE_START = "Fragment Lifecycle: ON_START"
        private const val LIFECYCLE_RESUME = "Fragment Lifecycle: ON_RESUME"
        private const val LIFECYCLE_DESTROY_VIEW = "Fragment Lifecycle: ON_DESTROY_VIEW"
        private const val LIFECYCLE_SAVE_INSTANCE_STATE = "Fragment Lifecycle: ON_SAVE_INSTANCE"
        private const val LIFECYCLE_DESTROY = "Fragment Lifecycle: ON_DESTROY"
    }

    //----------------------------------------------------------------------------------------------
    // Fragment UI state helpers
    //----------------------------------------------------------------------------------------------

    /**
     * Executes different actions based on the fragment UI state.
     *
     * @param instanceState Saved instance state bundle
     * @param recreating Executed when the fragment is being recreated
     * @param resumed Executed when the fragment is currently resumed
     */
    protected inline fun onFragmentUiState(
        instanceState: Bundle?,
        recreating: () -> Unit,
        resumed: () -> Unit
    ) {
        if (InstanceStateHandler.isRecreatingFragment(instanceState, lifecycle.currentState)) {
            recreating()
        }

        if (lifecycle.currentState == Lifecycle.State.RESUMED) {
            resumed()
        }
    }

    /**
     * Executes the provided block only when the fragment view
     * is being recreated.
     *
     * Useful for restoring UI-related state.
     */
    protected inline fun onRecreatingView(
        savedInstanceState: Bundle?,
        block: () -> Unit
    ) {
        if (InstanceStateHandler.isRecreatingFragment(savedInstanceState, lifecycle.currentState)) {
            block()
        }
    }

    /**
     * Executes the provided block only when the fragment
     * is in the RESUMED state.
     */
    protected inline fun onViewResumed(block: () -> Unit) {
        if (lifecycle.currentState == Lifecycle.State.RESUMED) {
            block()
        }
    }

    /**
     * Executes the provided block only when the fragment
     * is being initialized for the first time.
     */
    protected inline fun onInitializing(
        savedInstanceState: Bundle?,
        block: () -> Unit
    ) {
        if (InstanceStateHandler.isInitializingFragment(savedInstanceState)) {
            block()
        }
    }
}