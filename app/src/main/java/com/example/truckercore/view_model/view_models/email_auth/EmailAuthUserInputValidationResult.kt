package com.example.truckercore.view_model.view_models.email_auth

/**
 * Holds the result of validating user input for email, password, and password confirmation fields.
 *
 * This class is immutable and provides helper methods to accumulate validation errors
 * in a functional style using copy-on-write.
 *
 * @property emailError Error message related to the email input field, or null if no error.
 * @property passwordError Error message related to the password input field, or null if no error.
 * @property confirmationError Error message related to the password confirmation field, or null if no error.
 */
data class EmailAuthUserInputValidationResult(
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmationError: String? = null
) {

    /**
     * Represents the validation state of all three fields as a 3-digit binary code.
     *
     * Each digit represents one field:
     * - 1st digit: email (1 if there's an error, 0 otherwise)
     * - 2nd digit: password (1 if there's an error, 0 otherwise)
     * - 3rd digit: confirmation (1 if there's an error, 0 otherwise)
     *
     * Example: "101" means there's an error in email and confirmation, but not in password.
     */
    val errorCode: String
        get() = listOf(
            if (emailError != null) 1 else 0,
            if (passwordError != null) 1 else 0,
            if (confirmationError != null) 1 else 0
        ).joinToString(separator = "")

    /**
     * Indicates whether any of the fields contain validation errors.
     *
     * @return true if any field has an error; false otherwise.
     */
    val hasErrors: Boolean
        get() = emailError != null || passwordError != null || confirmationError != null

    /**
     * Returns a new instance of this class with the email error set.
     *
     * @param error The error message to assign to the email field.
     * @return A new validation result with the updated email error.
     */
    fun addEmailError(error: String): EmailAuthUserInputValidationResult {
        return this.copy(emailError = error)
    }

    /**
     * Returns a new instance of this class with the password error set.
     *
     * @param error The error message to assign to the password field.
     * @return A new validation result with the updated password error.
     */
    fun addPassError(error: String): EmailAuthUserInputValidationResult {
        return this.copy(passwordError = error)
    }

    /**
     * Returns a new instance of this class with the confirmation error set.
     *
     * @param error The error message to assign to the confirmation field.
     * @return A new validation result with the updated confirmation error.
     */
    fun addConfirmationError(error: String): EmailAuthUserInputValidationResult {
        return this.copy(confirmationError = error)
    }
}