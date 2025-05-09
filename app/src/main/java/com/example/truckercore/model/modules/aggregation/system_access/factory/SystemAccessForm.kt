package com.example.truckercore.model.modules.aggregation.system_access.factory

import com.example.truckercore._utils.classes.Email
import com.example.truckercore._utils.classes.FullName
import com.example.truckercore.model.infrastructure.security.data.enums.Role
import com.example.truckercore.model.modules.authentication.data.UID

/**
 * Data class representing the input required to create system access for a new user.
 *
 * This form encapsulates all necessary user-provided information used during the system
 * access onboarding process, including user identity, contact information, and role assignment.
 *
 * @property uid The unique identifier (UID) representing the user in the authentication system.
 * @property role The role assigned to the user (e.g., ADMIN, EMPLOYEE).
 * @property name The full name of the user.
 * @property email The user's email address.
 */
data class SystemAccessForm(
    val uid: UID,
    val role: Role,
    val name: FullName,
    val email: Email
)
