package com.example.truckercore.layers.presentation.login.view.fragments.user_profile

import com.example.truckercore.databinding.FragmentUserProfileBinding
import com.example.truckercore.layers.presentation.base.handlers.StateHandler
import com.example.truckercore.layers.presentation.common.LoadingDialog
import com.example.truckercore.layers.presentation.login.view_model.user_profile.helpers.UserProfileFragmentState

/**
 * State handler responsible for applying [UserProfileFragmentState] updates
 * to the UI components of [UserProfileFragment].
 *
 * This class manages:
 * - FAB enabled/disabled state based on validation rules.
 * - Displaying or hiding the [LoadingDialog] during processing states.
 */
class UserProfileFragmentStateHandler : StateHandler<FragmentUserProfileBinding>() {

    /**
     * Delegates handling of FAB state and loading dialog visibility
     * according to the current [state].
     *
     * @param dialog The loading dialog shown during creation/processing.
     * @param state The current UI state of the user profile screen.
     */
    fun handleState(dialog: LoadingDialog, state: UserProfileFragmentState) {
        handleFab(state)
        handleDialog(state, dialog)
    }

    /**
     * Enables or disables the FAB depending on whether the input is valid
     * and the user is ready to proceed with profile creation.
     *
     * @param state Represents current validation and creation state.
     */
    private fun handleFab(state: UserProfileFragmentState) {
        binding.fragUserNameFab.isEnabled = state.isReadyToCreate()
    }

    /**
     * Shows or dismisses the [LoadingDialog] based on whether the user
     * profile is currently being created.
     *
     * @param status The current UI state, used to determine dialog visibility.
     * @param dialog The dialog object used to indicate loading progress.
     */
    private fun handleDialog(status: UserProfileFragmentState, dialog: LoadingDialog) {
        if (status.isCreating()) dialog.show()
        else dialog.dismiss()
    }

}