package com.example.truckercore.model.infrastructure.integration.preferences.model

/**
 * Represents the steps involved in a user registration process.
 *
 * This sealed class ensures type safety and exhaustive handling of all possible registration steps.
 * Each step is represented as a singleton [data object], making it easy to use in `when` expressions
 * and preventing invalid states.
 */
sealed class RegistrationStep {

    /**
     * Represents the step where the user creates or provides their email address.
     */
    data object Email : RegistrationStep()

    /**
     * Represents the step where the user provides or updates their name.
     */
    data object Name : RegistrationStep()

    /**
     * Represents the step where the user's email address is verified.
     */
    data object EmailVerified : RegistrationStep()

    /**
     * Represents the final step where the environment or user context is fully prepared.
     *
     * This typically involves the creation and configuration of internal domain-related
     * objects required for each new user to operate within the application.
     */
    data object Prepared : RegistrationStep()

}