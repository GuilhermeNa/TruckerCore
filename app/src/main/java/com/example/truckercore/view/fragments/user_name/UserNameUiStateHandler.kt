package com.example.truckercore.view.fragments.user_name

import com.example.truckercore._utils.classes.ButtonState
import com.example.truckercore._utils.classes.FieldState
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout

class UserNameUiStateHandler(
    private val nameLayout: TextInputLayout,
    private val fab: FloatingActionButton
) {

    fun handleUiComponents(fieldState: FieldState, fabState: ButtonState) {
        handleNameLayout(fieldState)
        handleFab(fabState)
    }

    private fun handleNameLayout(fieldName: FieldState) {
        fieldName.handle(
            onError = ::showLayoutError,
            onNeutral = { clearLayoutError() },
            onValid = { clearLayoutError() }
        )
    }

    private fun showLayoutError(message: String) {
        if (nameLayout.error == null) {
            nameLayout.error = message
            nameLayout.errorIconDrawable = null
        }
    }

    private fun clearLayoutError() {
        if (nameLayout.error != null) {
            nameLayout.error = null
            nameLayout.errorIconDrawable = null
        }
    }

    private fun handleFab(buttonState: ButtonState) {
        when (buttonState.isEnabled) {
            true -> fab.show()
            false -> fab.hide()
        }
    }

}