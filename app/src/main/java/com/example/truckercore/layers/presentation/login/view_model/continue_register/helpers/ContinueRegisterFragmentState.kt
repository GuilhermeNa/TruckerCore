package com.example.truckercore.layers.presentation.login.view_model.continue_register.helpers

import com.example.truckercore.core.my_lib.classes.Email
import com.example.truckercore.layers.presentation.base.contracts.State
import com.example.truckercore.core.my_lib.ui_components.TextComponent

/**
 * Represents the UI state of the [ContinueRegisterFragment].
 *
 * It contains the information required to render the interface and determine.
 *
 * @property emailComponent A [TextComponent] displaying the user's email when available.
 * @property status A [ContinueRegisterFragmentStatus] indicating the current stage
 * of the registration flow (e.g., loading, email not verified, missing profile).
 */
data class ContinueRegisterFragmentState(
    val emailComponent: TextComponent = TextComponent(),
    val status: ContinueRegisterFragmentStatus = ContinueRegisterFragmentStatus.Loading
) : State {

    /**
     * Updates the state to reflect that the user's email has not yet been verified.
     *
     * The email is displayed on screen via the [emailComponent], and the [status]
     * is updated to [ContinueRegisterFragmentStatus.EmailNotVerified].
     *
     * @param email The user email used to update the UI.
     * @return A new [ContinueRegisterFragmentState] instance with updated fields.
     */
    fun emailNotVerified(email: Email) = copy(
        emailComponent = TextComponent(text = email.value),
        status = ContinueRegisterFragmentStatus.EmailNotVerified
    )

    /**
     * Updates the state to reflect that the user still needs to complete
     * their profile information.
     *
     * The email is displayed on screen via the [emailComponent], and the [status]
     * is updated to [ContinueRegisterFragmentStatus.MissingProfile].
     *
     * @param email The user email used to update the UI.
     * @return A new [ContinueRegisterFragmentState] instance with updated fields.
     */
    fun missingProfile(email: Email) = copy(
        emailComponent = TextComponent(text = email.value),
        status = ContinueRegisterFragmentStatus.MissingProfile
    )

    /**
     * Indicates whether the screen is currently in a loading state,
     * meaning the registration information is still being fetched.
     *
     * @return `true` if the current [status] is loading.
     */
    fun isLoading() = status.isLoading()

    /**
     * Indicates whether the user is currently in the "email not verified" state.
     *
     * @return `true` if the current [status] is [ContinueRegisterFragmentStatus.EmailNotVerified].
     */
    fun isEmailNotVerified() = status.isEmailNotVerified()

}