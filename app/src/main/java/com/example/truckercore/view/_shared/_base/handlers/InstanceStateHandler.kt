package com.example.truckercore.view._shared._base.handlers

import android.os.Bundle
import androidx.lifecycle.Lifecycle

interface InstanceStateHandler {

    companion object {
        private const val KEY_CREATED = "KEY_CREATED"
    }

    fun registerFragmentCreated(outState: Bundle) {
        outState.putBoolean(KEY_CREATED, true)
    }

    fun isRecreating(
        savedInstanceState: Bundle?,
        lifecycle: Lifecycle.State
    ): Boolean {
        return savedInstanceState?.let {
            lifecycle == Lifecycle.State.STARTED && it.getBoolean(KEY_CREATED)
        } ?: false
    }

}