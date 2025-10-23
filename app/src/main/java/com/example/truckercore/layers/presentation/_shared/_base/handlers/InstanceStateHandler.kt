package com.example.truckercore.layers.presentation._shared._base.handlers

import android.os.Bundle
import androidx.lifecycle.Lifecycle

/**
 * InstanceStateHandler is a utility interface that provides common methods
 * for working with the `savedInstanceState` Bundle in Fragments.
 *
 * Its main purpose is to centralize the logic for storing and retrieving
 * internal fragment state, allowing consistent behavior across multiple fragments.
 */
interface InstanceStateHandler {

    companion object {
        private const val KEY_CREATED = "KEY_CREATED"
    }

    /**
     * Marks the fragment as created by saving a flag (`true`) in the outState bundle.
     *
     * This should be called inside the Fragment's `onSaveInstanceState()` method.
     *
     * @param outState The Bundle in which to place the created flag.
     */
    fun registerFragmentCreated(outState: Bundle) {
        outState.putBoolean(KEY_CREATED, true)
    }

    /**
     * Returns true if the Fragment is being recreated (i.e., it has been previously created,
     * and its lifecycle is currently in the STARTED state).
     *
     * This helps distinguish whether the fragment is coming back from a recreation
     * (like orientation change or back stack restore).
     *
     * @param savedInstanceState The saved state Bundle from `onCreate()` or `onViewCreated()`.
     * @param lifecycle The current Lifecycle state of the Fragment.
     * @return True if the fragment is being recreated, false otherwise.
     */
    fun isRecreating(
        savedInstanceState: Bundle?,
        lifecycle: Lifecycle.State
    ): Boolean {
        return savedInstanceState?.let {
            lifecycle == Lifecycle.State.STARTED && it.getBoolean(KEY_CREATED)
        } ?: false
    }

    /**
     * Returns true if this is the first execution of the fragment (i.e., not a recreation).
     *
     * This method checks if the fragment has never been marked as "created" before.
     *
     * @param savedInstanceState The saved state Bundle from `onCreate()` or `onViewCreated()`.
     * @return True if the fragment is being created for the first time, false otherwise.
     */
    fun isFirstExecution(savedInstanceState: Bundle?): Boolean {
        return savedInstanceState?.let { !it.getBoolean(KEY_CREATED) } ?: false
    }

}