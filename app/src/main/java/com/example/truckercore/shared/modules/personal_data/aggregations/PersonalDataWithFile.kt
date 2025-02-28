package com.example.truckercore.shared.modules.personal_data.aggregations

import com.example.truckercore.shared.errors.InvalidStateException
import com.example.truckercore.shared.modules.file.entity.File
import com.example.truckercore.shared.modules.personal_data.entity.PersonalData

/**
 * Represents a personal data record along with associated files.
 * This class encapsulates a PersonalData entity and its related files.
 *
 * @property pData The Personal Data entity that this record is associated with.
 * @property files List of files related to the licensing record, defaults to an empty list.
 */
data class PersonalDataWithFile(
    val pData: PersonalData,
    val files: List<File> = emptyList()
) {

    init {
        if (files.any { it.parentId != pData.id }) {
            throw InvalidStateException("File does not belong to the provided Personal Data.")
        }
    }

    /**
     * Returns the parent ID associated with the personal data object. This value is retrieved from the `pData.parentId` property.
     *
     * This getter is used to obtain the unique identifier of the parent object to which the personal data (and associated files) is linked.
     */
    val parentId get() = pData.parentId

}
