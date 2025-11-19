package com.example.truckercore.layers.presentation.nav_login.view.fragments.welcome

import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.core.view.isVisible
import com.example.truckercore.core.my_lib.expressions.slideInBottom
import com.example.truckercore.core.my_lib.expressions.slideOutBottom
import com.example.truckercore.databinding.FragmentWelcomeBinding
import com.example.truckercore.layers.presentation.base.handlers.StateHandler

class WelcomeFragmentUiStateHandler : StateHandler<FragmentWelcomeBinding>() {

    private val fab get() = binding.fragWelcomeLeftFab

    fun animateFabIn() {
        if (!fab.isVisible) {
            fab.slideInBottom(200)
        }
    }

    fun animateFabOut() {
        if (fab.isVisible) {
            fab.slideOutBottom(INVISIBLE, 200)
        }
    }

    fun showFab() {
        if (!fab.isVisible) {
            fab.visibility = VISIBLE
        }
    }

}