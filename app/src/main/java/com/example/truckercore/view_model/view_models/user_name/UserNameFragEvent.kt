package com.example.truckercore.view_model.view_models.user_name

import com.example.truckercore.view.fragments.user_name.UserNameFragment

/**
 * Sealed class to represent various events that can be triggered in the [UserNameFragment].
 * These events are collected and handled by the [UserNameViewModel] to manage fragment interactions.
 */
sealed class UserNameFragEvent {

    /**
     * Event triggered when the Floating Action Button (FAB) is clicked.
     */
    data object FabCLicked : UserNameFragEvent()

    /**
     * Event triggered when the background (outside of the name input area) is clicked.
     */
    data object BackgroundClicked : UserNameFragEvent()

}