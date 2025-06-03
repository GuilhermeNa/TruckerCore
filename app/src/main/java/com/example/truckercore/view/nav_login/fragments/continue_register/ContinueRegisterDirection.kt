package com.example.truckercore.view.nav_login.fragments.continue_register

/**
 * Represents the possible navigation directions after the user completes the
 * continue registration flow.
 *
 * - [VERIFY_EMAIL]: Navigates the user to a screen where they must verify their email.
 * - [CREATE_USER]: Navigates the user to a screen where a new user account is created.
 */
enum class ContinueRegisterDirection {
    VERIFY_EMAIL,
    CREATE_USER
}