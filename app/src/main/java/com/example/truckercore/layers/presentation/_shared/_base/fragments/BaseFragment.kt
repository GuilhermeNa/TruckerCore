package com.example.truckercore.layers.presentation._shared._base.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.example.truckercore.core.expressions.getClassName
import com.example.truckercore.core.util.AppLogger
import com.example.truckercore.presentation._shared._base.handlers.InstanceStateHandler

abstract class BaseFragment : Fragment(), InstanceStateHandler {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppLogger.d(getClassName(), LIFECYCLE_CREATE)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AppLogger.d(getClassName(), LIFECYCLE_VIEW_CREATED)
    }

    override fun onStart() {
        super.onStart()
        AppLogger.d(getClassName(), LIFECYCLE_START)
    }

    override fun onResume() {
        super.onResume()
        AppLogger.d(getClassName(), LIFECYCLE_RESUME)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        AppLogger.d(getClassName(), LIFECYCLE_DESTROY_VIEW)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        registerFragmentCreated(outState)
        AppLogger.d(getClassName(), LIFECYCLE_SAVE_INSTANCE_STATE)
    }

    override fun onDestroy() {
        super.onDestroy()
        AppLogger.d(getClassName(), LIFECYCLE_DESTROY)
    }

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
    protected inline fun doIfRecreatingView(savedInstanceState: Bundle?, block: () -> Unit) {
        if (isRecreating(savedInstanceState, lifecycle.currentState)) {
            block()
        }
    }

    protected inline fun doIfResumedView(block: () -> Unit) {
        if (this.lifecycle.currentState == Lifecycle.State.RESUMED) block()
    }

    protected inline fun doOnInitialization(savedInstanceState: Bundle?, block: () -> Unit) {
        if(isFirstExecution(savedInstanceState)) block()
    }

}