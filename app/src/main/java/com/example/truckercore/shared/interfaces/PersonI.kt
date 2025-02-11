package com.example.truckercore.shared.interfaces

/**
 * Interface representing common properties of a person in the system.
 */
internal interface PersonI {

    /**
     * The unique identifier of the user. Can be null if not assigned.
     */
    val userId: String?

    /**
     * The full name of the person.
     */
    val name: String

    /**
     * The email address of the person.
     */
    val email: String

}