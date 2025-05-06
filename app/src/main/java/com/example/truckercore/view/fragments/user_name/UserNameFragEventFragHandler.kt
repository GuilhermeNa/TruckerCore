package com.example.truckercore.view.fragments.user_name

import com.example.truckercore._utils.expressions.hideKeyboard

class UserNameFragEventFragHandler(private val fragment: UserNameFragment) {

    private val binding = fragment.binding

    /**
     * Handles the click event for the FAB button.
     * Clears focus, hides the keyboard, and triggers a profile update.
     */
    fun handleFabClicked(updateProfile: (String) -> Unit) {
        clearFocusAndKeyboard()
        val name = binding.fragUserNameText.text.toString()
        updateProfile(name)
    }

    /**
     * Handles the click event for the background.
     * Clears focus and hides the keyboard.
     */
    fun handleBackgroundClicked() {
        clearFocusAndKeyboard()
    }

    /**
     * Clears the focus from the input field and hides the keyboard.
     */
    private fun clearFocusAndKeyboard() {
        binding.fragUserNameText.clearFocus()
        fragment.hideKeyboard()
    }

}