package com.example.truckercore.view_model.expressions

import com.example.truckercore.model.shared.utils.expressions.isNameFormat
import com.example.truckercore.view_model.errors.EmptyUserNameException
import com.example.truckercore.view_model.errors.IncompleteNameException
import com.example.truckercore.view_model.errors.InvalidSizeException
import com.example.truckercore.view_model.errors.WrongNameFormatException

/**
 * Function to validate the username according to specific rules
 *
 * This function checks the following conditions:
 * 1. If the username is empty after trimming.
 * 2. If the username's size is within a valid range (between 5 and 29 characters).
 * 3. If the username contains both first and last names (i.e., not just a single word).
 * 4. If any word in the username contains invalid characters (non-alphabetic).
 *
 * @throws EmptyUserNameException If the username is empty after trimming.
 * @throws InvalidSizeException If the username size is not within the valid range (5 to 29 characters).
 * @throws IncompleteNameException If the username does not contain a first and last name.
 * @throws WrongNameFormatException If any word in the username contains invalid characters.
 */
fun String.validateUserName() {
    val trimmedName = this.trimStart().trimEnd()
    val wordArr = this.split(" ")

    when {
        trimmedName.isEmpty() -> throw EmptyUserNameException("Username cannot be empty.")
        !trimmedName.sizeIsValid() -> throw InvalidSizeException("Username size must be between 5 and 29 characters.")
        wordArr.size == 1 -> throw IncompleteNameException("Complete name (first and last name) is required.")
        trimmedName.isAnyWordInWrongFormat() -> throw WrongNameFormatException("Username contains invalid characters or format.")
    }

}

private fun String.sizeIsValid(): Boolean = length in 5..29

private fun String.isAnyWordInWrongFormat(): Boolean {
    val wordArr = this.split(" ")
    return wordArr.any {
        !it.isNameFormat()
    }
}