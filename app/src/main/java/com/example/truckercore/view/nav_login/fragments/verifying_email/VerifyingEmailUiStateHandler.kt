package com.example.truckercore.view.nav_login.fragments.verifying_email

import com.example.truckercore.R
import com.example.truckercore.databinding.FragmentVerifyingEmailBinding
import com.example.truckercore.view._shared._base.handlers.StateHandler
import com.example.truckercore.view_model._shared.components.TextComponent

class VerifyingEmailUiStateHandler : StateHandler<FragmentVerifyingEmailBinding>() {

    fun handleComponent(emailComponent: TextComponent) {
        getBinding().fragVerifyingEmailTextEmail.text = emailComponent.text
    }

    fun jumpToEnd() {
        getBinding().fragVerifyingEmailMotionLayout.jumpToState(R.id.frag_verifying_email_scene_end)
    }

    fun transitionToEnd() {
        getBinding().fragVerifyingEmailMotionLayout.transitionToEnd()
    }

    fun animateProgress(value: Int) {
        getBinding().fragVerifyingEmailProgressbar.setProgressCompat((PROGRESS_MAX - value), true)
        getBinding().fragVerifyingEmailTimer.text = "$value"
    }

    fun jumpToProgress(value: Int) {
        getBinding().fragVerifyingEmailProgressbar.setProgressCompat((PROGRESS_MAX - value), false)
        getBinding().fragVerifyingEmailTimer.text = "$value"
    }

    companion object {
        private const val PROGRESS_MAX = 61
    }

}