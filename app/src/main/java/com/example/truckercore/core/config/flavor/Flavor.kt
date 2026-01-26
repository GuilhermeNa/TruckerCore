package com.example.truckercore.core.config.flavor

/**
 * Represents an application flavor configuration.
 *
 * Implementations define flavor-specific metadata used to
 * customize application behavior and identification.
 */
interface Flavor {

    /**
     * The display name of the application for this flavor.
     */
    val appName: String

}