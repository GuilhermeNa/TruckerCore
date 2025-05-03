package com.example.truckercore.model.configs.build

/**
 * Provides information about the current application flavor.
 *
 * Implementations of this interface are responsible for determining
 * which product flavor is currently running, typically based on
 * application metadata such as the app name or package.
 *
 * Useful in multi-flavor Android applications to conditionally execute
 * logic depending on the active variant (e.g., admin, driver, customer).
 */
interface FlavorService {

    /**
     * Returns the current app flavor.
     *
     * @return The [Flavor] representing the current app variant.
     * @throws BuildException if the flavor cannot be determined.
     */
    fun getFlavor(): Flavor

}