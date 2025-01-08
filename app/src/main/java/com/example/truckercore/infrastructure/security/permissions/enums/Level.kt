package com.example.truckercore.infrastructure.security.permissions.enums

/**
 * Enum class representing different roles or permission levels within the system.
 * Each level corresponds to a different set of privileges and access rights.
 */
enum class Level {

    /**
     * Represents the top-most privileged role in the system, with complete and unrestricted access to all areas and functionalities.
     */
    MASTER,

    /**
     * Represents a high-level access user, but does not have full control over the system.
     */
    ADMIN,

    /**
     * Represents a user with the ability to moderate content or manage user actions.
     */
    MODERATOR,

    /**
     * Represents a user assigned to driving tasks in the system, typically in logistics or transport-related systems.
     */
    DRIVER;

}