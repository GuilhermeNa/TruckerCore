package com.example.truckercore.shared.modules.personal_data.aggregations

import com.example.truckercore.shared.modules.personal_data.entity.PersonalData
import com.example.truckercore.shared.modules.file.entity.File

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

    val parentId get() = pData.parentId

}
