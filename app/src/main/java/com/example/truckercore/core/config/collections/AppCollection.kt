package com.example.truckercore.core.config.collections

/**
 * Defines the names of the collections used by the application APIs
 * to persist and retrieve data.
 *
 * Each enum value represents a specific collection where data
 * related to a given domain or role is stored.
 */
enum class AppCollection {
    COMPANY,
    USER,
    ADMIN,
    AUTONOMOUS,
    DRIVER;
}