package com.example.truckercore.shared.interfaces

import com.example.truckercore.shared.modules.personal_data.entity.PersonalData
import com.example.truckercore.shared.modules.storage_file.entities.StorageFile

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

    /**
     * The person's photo, represented by a storage file. Can be null.
     */
    val photo: StorageFile?

    /**
     * An optional list of personal data associated with the person. Can be null.
     */
    val personalData: List<PersonalData>?

}