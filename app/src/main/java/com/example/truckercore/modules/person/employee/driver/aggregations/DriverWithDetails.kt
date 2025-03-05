package com.example.truckercore.modules.person.employee.driver.aggregations

import com.example.truckercore.modules.person.employee.driver.entity.Driver
import com.example.truckercore.shared.errors.InvalidStateException
import com.example.truckercore.shared.modules.file.entity.File
import com.example.truckercore.shared.modules.personal_data.aggregations.PersonalDataWithFile

/**
 * Represents a driver with additional details such as their photo and associated personal data with files.
 * This class includes validation logic to ensure that the photo and personal data belong to the provided driver.
 *
 * @property driver The main [Driver] entity containing essential information about the driver.
 * @property photo An optional [File] representing the driver's photo. The photo must belong to the provided driver.
 * @property personalDataWithFile A set of [PersonalDataWithFile], which contains the personal data and its associated files.
 *         Each [PersonalDataWithFile] must belong to the provided driver.
 *
 * @throws InvalidStateException If the photo or any personal data does not belong to the provided driver.
 */
data class DriverWithDetails(
    val driver: Driver,
    val photo: File? = null,
    val personalDataWithFile: Set<PersonalDataWithFile> = emptySet()
) {

    init {

        // Validate photo
        photo?.let { validPhoto ->
            if (validPhoto.parentId != driver.id)
                throw InvalidStateException("Photo does not belong to the provided driver.")
        }

        // Validate PDataWF
        personalDataWithFile.forEach { pDataWF ->
            if (pDataWF.pData.parentId != driver.id)
                throw InvalidStateException("Personal data does not belong to the provided driver.")
        }

    }

}
