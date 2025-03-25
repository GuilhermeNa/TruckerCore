package com.example.truckercore.view_model.view_models.welcome_fragment

/**
 * A sealed class representing the events that can be triggered within the `WelcomeFragment`.
 * This class encapsulates three distinct UI events that are triggered by user interactions with the fragment's UI elements:
 *
 * - `LeftFabCLicked`: Triggered when the left floating action button (FAB) is clicked.
 * - `RightFabClicked`: Triggered when the right floating action button (FAB) is clicked.
 * - `TopButtonClicked`: Triggered when the top button is clicked.
 *
 * Sealed classes allow for a controlled set of subclasses, ensuring exhaustiveness checking in `when` expressions.
 * This class is used to represent and handle user interactions with the fragment's UI components.
 */
sealed class WelcomeFragEvent {

    /**
     * Event triggered when the left floating action button (FAB) is clicked.
     * Typically used to handle the back navigation or previous actions.
     */
    data object LeftFabCLicked : WelcomeFragEvent()

    /**
     * Event triggered when the right floating action button (FAB) is clicked.
     * Typically used to handle forward navigation or next actions.
     */
    data object RightFabClicked : WelcomeFragEvent()

    /**
     * Event triggered when the top button is clicked.
     * This can be used to trigger a special action or navigation, such as proceeding to the next fragment.
     */
    data object TopButtonClicked: WelcomeFragEvent()

}