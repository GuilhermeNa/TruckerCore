package com.example.truckercore.model.infrastructure.integration.auth.for_app.data

import com.example.truckercore._utils.classes.FullName

/**
 * Represents the profile information of a user within the application.
 *
 * This data class encapsulates user-specific metadata such as their full name.
 * It can be extended in the future to include additional fields like profile picture,
 * phone number, or other optional attributes.
 *
 * ### Example:
 * ```
 * val profile = UserProfile(fullName = FullName("Jane Doe"))
 * ```
 *
 * @property fullName The full name of the user.
 */
data class UserCategory(val fullName: FullName)