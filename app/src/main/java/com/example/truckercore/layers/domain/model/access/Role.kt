package com.example.truckercore.layers.domain.model.access

/**
 * Defines the available system roles.
 *
 * Roles directly affect authorization and permission levels within the application.
 * Each role determines which features, operations, and data the user is allowed
 * to access or manipulate.
 *
 * Changes to roles or role assignments have a direct impact on the security model
 * and behavior of the application.
 */
enum class Role {
    ADMIN,
    STAFF,
    DRIVER,
    AUTONOMOUS
}