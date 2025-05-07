package com.example.truckercore.view_model.view_models.splash

import com.example.truckercore.model.configs.flavor.Flavor

/**
 * Represents the different UI states of the splash screen.
 *
 * Each state is associated with a [flavor] which contains information about the application's configuration.
 */
sealed class SplashUiState(open val flavor: Flavor) {
    /**
     * Represents the initial state of the splash screen.
     * Typically shown when the app is launched and the animation is starting.
     *
     * @param flavor The application's configuration or flavor.
     */
    data class Initial(override val flavor: Flavor): SplashUiState(flavor)

    /**
     * Represents the loading state of the splash screen.
     * This state is used when the app is waiting for data to load or some background process is ongoing.
     *
     * @param flavor The application's configuration or flavor.
     */
    data class Loading(override val flavor: Flavor): SplashUiState(flavor)

    /**
     * Represents the loaded state of the splash screen.
     * This state indicates that the app has finished loading necessary data or processes.
     *
     * @param flavor The application's configuration or flavor.
     */
    data class Loaded(override val flavor: Flavor): SplashUiState(flavor)
}