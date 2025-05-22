package com.example.truckercore.view.fragments.welcome

import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.core.view.isVisible
import com.example.truckercore._utils.expressions.slideInBottom
import com.example.truckercore._utils.expressions.slideOutBottom
import com.google.android.material.floatingactionbutton.FloatingActionButton

class WelcomeUiStateHandler(private val fab: FloatingActionButton) {

    fun animateLeftFabIn() {
        fab.slideInBottom(200)
    }

    fun animateLeftFabOut() {
        fab.slideOutBottom(INVISIBLE, 200)
    }

    fun showLeftFab() {
        if (!fab.isVisible) fab.visibility = VISIBLE
    }


}