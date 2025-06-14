package com.example.truckercore.view.nav_login.fragments.continue_register

import android.content.Context
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.content.ContextCompat
import com.example.truckercore.R
import com.example.truckercore.databinding.FragmentContinueRegisterBinding
import com.example.truckercore.view._shared._base.handlers.StateHandler
import com.example.truckercore.view_model._shared.components.TextComponent
import com.example.truckercore.view_model.view_models.continue_register.state.ContinueRegisterDirection
import com.example.truckercore.view_model.view_models.continue_register.state.ContinueRegisterState

class ContinueRegisterStateHandler : StateHandler<FragmentContinueRegisterBinding>() {

    fun replaceShimmerForContent() {
        getBinding().apply {
            // Hide Shimmer
            fragContinueRegisterCardShimmer.stopShimmer()
            fragContinueRegisterCardShimmer.visibility = GONE
            fragContinueRegisterLayoutButtonShimmer.stopShimmer()
            fragContinueRegisterLayoutButtonShimmer.visibility = GONE

            // Show Content
            fragContinueRegisterLayoutButton.visibility = VISIBLE
            fragContinueRegisterCard.visibility = VISIBLE
        }
    }

    fun handleUiComponents(context: Context, state: ContinueRegisterState) {
        bindEmailComponent(state.emailComponent)
        bindEmailVerifiedStatus(context, state.direction)
    }

    private fun bindEmailComponent(emailComponent: TextComponent) {
        val view = getBinding().fragContinueRegisterEmailText
        bindText(emailComponent, view)
    }

    private fun bindEmailVerifiedStatus(context: Context, direction: ContinueRegisterDirection?) {
        if (direction == ContinueRegisterDirection.CREATE_USER) {
            val drawable = ContextCompat.getDrawable(context, R.drawable.icon_check)

            getBinding()
                .fragContinueRegisterEmailStatusText
                .setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, null, null, null)
        }
    }

}