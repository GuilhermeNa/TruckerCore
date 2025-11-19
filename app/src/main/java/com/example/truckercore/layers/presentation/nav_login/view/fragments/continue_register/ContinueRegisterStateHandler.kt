package com.example.truckercore.layers.presentation.nav_login.view.fragments.continue_register

import android.content.Context
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.content.ContextCompat
import com.example.truckercore.R
import com.example.truckercore.databinding.FragmentContinueRegisterBinding
import com.example.truckercore.layers.presentation.base.handlers.StateHandler
import com.example.truckercore.layers.presentation.base.components.TextComponent
import com.example.truckercore.layers.presentation.viewmodels.view_models.continue_register.state.ContinueRegisterFragmentState
import com.example.truckercore.layers.presentation.viewmodels.view_models.continue_register.state.ContinueRegisterFragmentStatus

/**
 * Handles all UI state rendering for the [ContinueRegisterFragment].
 *
 * This class is responsible for updating visible components based on the
 * [ContinueRegisterFragmentState] emitted by the ViewModel. It manages:
 *
 * - Displaying the user's email.
 * - Setting status icons that represent whether the email has been verified.
 * - Replacing the shimmer placeholders with the actual content once loading is complete.
 *
 * The handler abstracts UI updates away from the Fragment, keeping the Fragment
 * focused on lifecycle and event wiring while state rendering is centralized here.
 */
class ContinueRegisterStateHandler : StateHandler<FragmentContinueRegisterBinding>() {

    /**
     * Renders the current registration UI state.
     *
     * - If the state is still loading, this function stops immediately.
     * - Once the loading finishes, the handler:
     *   1. Displays the email text.
     *   2. Applies the correct drawable (✔ or X) depending on the registration status.
     *   3. Replaces the shimmer with the actual content.
     *
     * @param context The context used for resolving drawable resources.
     * @param state The current UI state for the Continue Register screen.
     */
    fun handleState(context: Context, state: ContinueRegisterFragmentState) {
        if (state.isLoading()) return

        handleEmailComponent(state.emailComponent)
        handleEmailDrawable(context, state.status)
        replaceShimmerForContent()
    }

    /**
     * Binds the provided email text to the email TextView on the screen.
     *
     * @param emailComponent The component containing the user's email text.
     */
    private fun handleEmailComponent(emailComponent: TextComponent) {
        val textView = binding.fragContinueRegisterEmailText
        bindText(emailComponent, textView)
    }

    /**
     * Displays a check icon next to the email text when the email
     * has already been verified.
     *
     * A default (X) icon is displayed if the email is not verified.
     *
     * @param context Used to retrieve drawable resources.
     * @param status Represents the current registration status.
     */
    private fun handleEmailDrawable(context: Context, status: ContinueRegisterFragmentStatus) {
        if (status.isEmailVerified()) {
            val drawable = ContextCompat.getDrawable(context, R.drawable.icon_check)

            binding.fragContinueRegisterEmailStatusText
                .setCompoundDrawablesRelativeWithIntrinsicBounds(
                    drawable, null, null, null
                )
        }
    }

    /**
     * Hides all shimmer components and reveals the actual content of the screen.
     *
     * This is called once the initial loading phase is complete.
     */
    private fun replaceShimmerForContent() {
        binding.apply {
            // Hide shimmer placeholders
            fragContinueRegisterCardShimmer.stopShimmer()
            fragContinueRegisterCardShimmer.visibility = GONE
            fragContinueRegisterLayoutButtonShimmer.stopShimmer()
            fragContinueRegisterLayoutButtonShimmer.visibility = GONE

            // Show actual content
            fragContinueRegisterLayoutButton.visibility = VISIBLE
            fragContinueRegisterCard.visibility = VISIBLE
        }
    }

}