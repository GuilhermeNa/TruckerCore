package com.example.truckercore.view.nav_login.fragments.continue_register

/**
 * Represents the visual status level used to display progress indicators
 * (e.g., via level-list drawables) in the continue registration flow.
 *
 * - [UNDONE]: Indicates that the step has not been completed.
 * - [DONE]: Indicates that the step has been successfully completed.
 *
 * Each enum maps to an integer level used by a level-list drawable.
 *
 * @property value The level value corresponding to the drawable state.
 */
enum class ContinueRegisterImageLevel(val value: Int) {
    UNDONE(1),
    DONE(2)
}