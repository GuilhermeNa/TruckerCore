package com.example.truckercore.view.fragments.user_name

import androidx.lifecycle.Lifecycle
import com.example.truckercore.R
import com.example.truckercore._utils.expressions.onLifecycleState
import com.example.truckercore.view.dialogs.LoadingDialog


class UserNameFragStateHandler(private val fragment: UserNameFragment) {

    private val binding = fragment.binding
    private val context = fragment.binding.root.context
    private val dialog by lazy { LoadingDialog(context) }

    /**
     * Handles the initial state of the fragment by resetting UI components.
     */
    fun handleInitialState() {
        dismissDialog()
        setLayoutToInitialState()
    }

    /**
     * Handles the updating state by showing the loading dialog and preparing the UI.
     */
    fun handleUpdatingState() {
        setLayoutToInitialState()
        dialog.show()
    }

    /**
     * Handles the error state by displaying an error message and updating the layout.
     * Transitions the MotionLayout to an error state.
     */
    fun handleUserInputErrorState(
        text: String,
        currentState: Lifecycle.State
    ) {
        dismissDialog()

        // Set the UI message
        binding.fragUserNameError.text = text

        // Change UI state to error
        val motionLayout = binding.fragUserNameMotion
        fragment.onLifecycleState(
            resumed = { motionLayout.transitionToEnd() },
            anyOther = { motionLayout.jumpToState(R.id.frag_user_name_scene_state_end) }
        )
    }

    /**
     * Resets the layout to its initial state, clearing the error message and resetting the MotionLayout.
     */
    private fun setLayoutToInitialState() {
        binding.fragUserNameMotion.run {
            if (this.currentState == R.id.frag_user_name_scene_state_end) {
                binding.fragUserNameError.text = null
                this.transitionToStart()
            }
        }
    }

    /**
     * Dismisses the loading dialog if it is currently being displayed.
     */
    private fun dismissDialog() {
        if (dialog.isShowing) {
            dialog.dismiss()
        }
    }

}