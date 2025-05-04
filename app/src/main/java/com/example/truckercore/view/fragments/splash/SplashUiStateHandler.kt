package com.example.truckercore.view.fragments.splash

import android.widget.TextView
import androidx.constraintlayout.motion.widget.MotionLayout
import com.example.truckercore.R
import com.example.truckercore.model.configs.build.Flavor
import com.example.truckercore.view_model.view_models.splash.SplashUiState

class SplashUiStateHandler(
    val motionLayout: MotionLayout,
    val textView: TextView
) {

    val secondUiState = R.id.frag_verifying_email_state_2
    private val thirdUiState = R.id.frag_verifying_email_state_3

    fun bindAppName(flavor: Flavor) {
        textView.text = flavor.description
    }

    fun runFirstUiTransition() {
        motionLayout.transitionToState(secondUiState)
    }

    fun jumpToSecondUiState() {
        motionLayout.jumpToState(secondUiState)
    }

    fun runSecondUiTransition() {
        motionLayout.transitionToState(secondUiState)
    }

    fun jumpToThirdUiState() {
        motionLayout.jumpToState(thirdUiState)
    }

}