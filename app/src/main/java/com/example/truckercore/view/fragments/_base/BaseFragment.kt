package com.example.truckercore.view.fragments._base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.truckercore._utils.expressions.getClassName
import com.example.truckercore.model.logger.AppLogger

abstract class BaseFragment : Fragment() {

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
        private const val LIFECYCLE_DESTROY = "Fragment Lifecycle: ON_DESTROY"
    }

}