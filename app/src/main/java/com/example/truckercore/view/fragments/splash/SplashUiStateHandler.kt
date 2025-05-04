package com.example.truckercore.view.fragments.splash

import android.widget.TextView
import androidx.constraintlayout.motion.widget.MotionLayout
import com.example.truckercore.R
import com.example.truckercore.view_model.view_models.splash.SplashUiState

class SplashUiStateHandler(
    val motionLayout: MotionLayout,
    val textView: TextView
) {

    fun bindAppName(state: SplashUiState) {
        textView.text = state.flavor.description
    }

    fun handleUiTransition(state: SplashUiState, animated: Boolean) {
        val secondUiState = R.id.frag_verifying_email_state_2
        val thirdUiState = R.id.frag_verifying_email_state_3

        when(state) {
            is SplashUiState.Initial -> {
               motionLayout.transitionToState(secondUiState)
            }
            is SplashUiState.Loading -> {

            }
            is SplashUiState.Loaded -> {
                if(animated) motionLayout.transitionToState(thirdUiState)
                else motionLayout.jumpToState(thirdUiState)
            }

        }
    }




}