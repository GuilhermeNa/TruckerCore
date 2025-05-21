package com.example.truckercore.view.fragments._base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.truckercore._utils.expressions.getName
import com.example.truckercore.model.logger.AppLogger

abstract class BaseFragment: Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppLogger.d(getName(), FRAGMENT_STATE_CREATE)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AppLogger.d(getName(), FRAGMENT_STATE_VIEW_CREATED)
    }

    override fun onStart() {
        super.onStart()
        AppLogger.d(getName(), FRAGMENT_STATE_START)
    }

    override fun onResume() {
        super.onResume()
        AppLogger.d(getName(), FRAGMENT_STATE_RESUME)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        AppLogger.d(getName(), FRAGMENT_STATE_DESTROY_VIEW)
    }

    private companion object {
        private const val FRAGMENT_STATE_CREATE = "Fragment State: ON_CREATE"
        private const val FRAGMENT_STATE_VIEW_CREATED = "Fragment State: ON_VIEW_CREATED"
        private const val FRAGMENT_STATE_START = "Fragment State: ON_START"
        private const val FRAGMENT_STATE_RESUME = "Fragment State: ON_RESUME"
        private const val FRAGMENT_STATE_DESTROY_VIEW = "Fragment State: ON_DESTROY_VIEW"
    }

}