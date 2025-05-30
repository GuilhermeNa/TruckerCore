package com.example.truckercore.view.fragments.welcome

import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.core.view.isVisible
import com.example.truckercore._utils.expressions.slideInBottom
import com.example.truckercore._utils.expressions.slideOutBottom
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.lang.ref.WeakReference

class WelcomeUiStateHandler {

    private var _fab: WeakReference<FloatingActionButton>? = null

    private fun getFab(): FloatingActionButton =
        requireNotNull(_fab?.get()) { "FAB was not set or already collected." }

    fun setFab(fab: FloatingActionButton) {
        _fab = WeakReference(fab)
    }

    fun animateLeftFabIn() {
        getFab().slideInBottom(200)
    }

    fun animateLeftFabOut() {
        getFab().slideOutBottom(INVISIBLE, 200)
    }

    fun showLeftFab() {
        if (!getFab().isVisible) getFab().visibility = VISIBLE
    }

}