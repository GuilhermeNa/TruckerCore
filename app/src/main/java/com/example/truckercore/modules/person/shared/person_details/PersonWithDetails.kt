package com.example.truckercore.modules.person.shared.person_details

import com.example.truckercore.shared.abstractions.Person
import com.example.truckercore.shared.errors.InvalidStateException
import com.example.truckercore.shared.modules.file.entity.File
import com.example.truckercore.shared.modules.personal_data.aggregations.PersonalDataWithFile

/**
 * Represents a person with additional details, including a photo and a set of personal data entries.
 *
 * @param person The basic person details (a `Person` object).
 * @param photo An optional `File` representing the person's photo. Default value is `null`.
 * @param pDataWFSet A set of personal data entries with associated files. Default value is an empty set.
 *
 * @throws InvalidStateException If the `parentId` of the photo or any `pData` does not match the `id` of the `person`.
 * @throws NullPointerException If the `userId` of the `person` is null.
 */
data class PersonWithDetails(
    val person: Person,
    val photo: File? = null,
    val pDataWFSet: Set<PersonalDataWithFile> = emptySet()
) {

    init {

        // Validate photo
        photo?.let { validPhoto ->
            if (validPhoto.parentId != person.id)
                throw InvalidStateException("Photo does not belong to the provided person.")
        }

        // Validate PDataWF
        pDataWFSet.forEach { pDataWF ->
            if (pDataWF.parentId != person.id)
                throw InvalidStateException("Personal data does not belong to the provided person.")
        }

    }

    /**
     * Returns the user ID associated with the person object. This value is retrieved from the `person.userId` property.
     *
     * If the `userId` is null, a `NullPointerException` will be thrown, indicating that the user ID is missing
     * for the person object with the specified ID.
     */
    val userId
        get() = person.userId ?: throw NullPointerException(
            "Person User id is null for ${person::class.java} with id: ${person.id}."
        )

}
