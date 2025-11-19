package com.example.truckercore.layers.presentation.nav_login.view_model.email_auth.state

import com.example.truckercore.core.my_lib.classes.Email
import com.example.truckercore.core.my_lib.classes.EmailCredential
import com.example.truckercore.core.my_lib.classes.Password
import com.example.truckercore.layers.presentation.nav_login.fragments.email_auth.EmailAuthFragment
import com.example.truckercore.layers.presentation.base.contracts.State

private typealias UiComponents = com.example.truckercore.layers.presentation.nav_login.view_model.email_auth.state.EmailAuthUiComponents
private typealias Status = EmailAuthUiStatus

/**
 * Represents the current UI state of the [EmailAuthFragment] screen.
 *
 * This data class combines the input components [EmailAuthUiComponents] and the
 * high-level UI status [EmailAuthUiStatus] to provide a complete snapshot of the
 * screen's state at any given time.
 *
 * It contains helper functions to update each component, transition the UI status,
 * and generate the necessary credentials for authentication.
 */
data class EmailAuthenticationFragmentState(
    val uiComponents: UiComponents = UiComponents(),
    val status: Status = EmailAuthUiStatus.WaitingInput
) : State {

    /**
     * Sets the state to [EmailAuthUiStatus.WaitingInput], indicating
     * that the user has not yet provided valid input.
     */
    fun waitingInput() = copy(status = EmailAuthUiStatus.WaitingInput)

    /**
     * Sets the state to [EmailAuthUiStatus.ReadyToCreate], indicating
     * that all input fields are valid and account creation can proceed.
     */
    fun readyToCreate() = copy(status = EmailAuthUiStatus.ReadyToCreate)

    /**
     * Sets the state to [EmailAuthUiStatus.Creating], indicating
     * that an authentication task is in progress.
     */
    fun creating() = copy(status = EmailAuthUiStatus.Creating)

    /**
     * Updates the email component with [email] and recalculates
     * the overall UI status.
     *
     * Returns a new instance of [EmailAuthenticationFragmentState].
     */
    fun updateEmail(email: String): EmailAuthenticationFragmentState {
        val newComponents = uiComponents.updateEmail(email)
        val newStatus = determineStatus(newComponents)
        return copy(uiComponents = newComponents, status = newStatus)
    }

    /**
     * Updates the password component with [password] and recalculates
     * the overall UI status.
     *
     * Returns a new instance of [EmailAuthenticationFragmentState].
     */
    fun updatePassword(password: String): EmailAuthenticationFragmentState {
        val newComponents = uiComponents.updatePassword(password)
        val newStatus = determineStatus(newComponents)
        return copy(uiComponents = newComponents, status = newStatus)
    }

    /**
     * Updates the confirmation component with [confirmation] and recalculates
     * the overall UI status.
     *
     * Returns a new instance of [EmailAuthenticationFragmentState].
     */
    fun updateConfirmation(confirmation: String): EmailAuthenticationFragmentState {
        val newComponents = uiComponents.updateConfirmation(confirmation)
        val newStatus = determineStatus(newComponents)
        return copy(uiComponents = newComponents, status = newStatus)
    }

    /**
     * Determines the high-level UI status based on the validity of
     * all components.
     *
     * @return If all components are valid, returns [EmailAuthUiStatus.ReadyToCreate],
     * otherwise returns [EmailAuthUiStatus.WaitingInput].
     */
    private fun determineStatus(components: UiComponents) =
        if (isReadyToCreate(components)) EmailAuthUiStatus.ReadyToCreate
        else EmailAuthUiStatus.WaitingInput

    /**
     * Checks whether all UI components are valid and ready for account creation.
     */
    private fun isReadyToCreate(components: UiComponents = uiComponents): Boolean =
        components.emailComponent.isValid &&
                components.passwordComponent.isValid &&
                components.confirmationComponent.isValid

    /**
     * Generates an [EmailCredential] object from the current UI components.
     *
     * This object can be used to perform the authentication request.
     */
    fun getCredential() = EmailCredential(
        Email.from(uiComponents.emailComponent.text),
        Password.from(uiComponents.passwordComponent.text)
    )

    /**
     * Sets an error on the email component indicating a user collision,
     * and updates the UI status to [EmailAuthUiStatus.WaitingInput].
     *
     * Returns a new instance of [EmailAuthenticationFragmentState].
     */
    fun warnUserCollision(): EmailAuthenticationFragmentState {
        val newComponents = uiComponents.warnUserCollision()
        val newStatus = EmailAuthUiStatus.WaitingInput
        return copy(uiComponents = newComponents, status = newStatus)
    }

}