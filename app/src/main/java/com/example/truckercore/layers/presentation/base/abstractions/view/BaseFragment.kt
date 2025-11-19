package com.example.truckercore.layers.presentation.base.abstractions.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.example.truckercore.core.my_lib.expressions.getClassName
import com.example.truckercore.infra.logger.AppLogger
import com.example.truckercore.layers.presentation.base.contracts.BaseNavigator
import com.example.truckercore.layers.presentation.base.objects.InstanceStateHandler.isInitializingFragment
import com.example.truckercore.layers.presentation.base.objects.InstanceStateHandler.isRecreatingFragment
import com.example.truckercore.layers.presentation.base.objects.InstanceStateHandler.markAsCreated

abstract class BaseFragment : Fragment(), BaseNavigator {

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
        outState.markAsCreated()
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
    protected inline fun onFragmentUiState(
        savedInstanceState: Bundle?,
        recreating: () -> Unit,
        resumed: () -> Unit
    ) {
        if (isRecreatingFragment(savedInstanceState, lifecycle.currentState)) recreating()
        if(this.lifecycle.currentState == Lifecycle.State.RESUMED) resumed()
    }

    protected inline fun onRecreatingView(savedInstanceState: Bundle?, block: () -> Unit) {
        if (isRecreatingFragment(savedInstanceState, lifecycle.currentState)) {
            block()
        }
    }

    protected inline fun onViewResumed(block: () -> Unit) {
        if (this.lifecycle.currentState == Lifecycle.State.RESUMED) block()
    }

    protected inline fun onInitializing(savedInstanceState: Bundle?, block: () -> Unit) {
        if (isInitializingFragment(savedInstanceState)) block()
    }

}