package com.example.truckercore.modules.person.employee.admin.aggregations

import com.example.truckercore.modules.person.employee.admin.entity.Admin
import com.example.truckercore.shared.errors.InvalidStateException
import com.example.truckercore.shared.modules.file.entity.File
import com.example.truckercore.shared.modules.personal_data.aggregations.PersonalDataWithFile

/**
 * Represents an administrator along with additional details such as their photo and personal data.
 *
 * This data class is designed to hold information about an admin user and optionally include additional details
 * like the admin's photo and personal data files associated with the admin.
 * It is primarily used to provide a complete set of data regarding an admin, which can be useful when showing detailed
 * information of an admin in a UI or for processing administrative tasks.
 *
 * @property admin The [Admin] object representing the admin's basic details (e.g., name, email, role).
 * @property photo An optional [File] representing the photo of the admin. This is used for storing or displaying the admin's photo.
 * @property personalDataWithFile An optional [Set] of [PersonalDataWithFile] objects, each representing a piece of personal data
 *                              along with an associated file (if any). This may be used to store sensitive information related
 *                              to the admin, such as documents or attachments.
 * @throws InvalidStateException if the photo or personal data does not match the administrator's ID.
 */
data class AdminWithDetails(
    val admin: Admin,
    val photo: File? = null,
    val personalDataWithFile: Set<PersonalDataWithFile> = emptySet()
) {

    init {

        // Validate photo
        photo?.let { validPhoto ->
            if (validPhoto.parentId != admin.id)
                throw InvalidStateException("Photo does not belong to the provided admin.")
        }

        // Validate PDataWF
        personalDataWithFile.forEach { pDataWf ->
            if (pDataWf.pData.parentId != admin.id)
                throw InvalidStateException("Personal data does not belong to the provided admin.")
        }

    }

}
