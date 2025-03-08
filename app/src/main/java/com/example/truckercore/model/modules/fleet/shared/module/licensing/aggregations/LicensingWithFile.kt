package com.example.truckercore.model.modules.fleet.shared.module.licensing.aggregations

import com.example.truckercore.model.modules.fleet.shared.module.licensing.entity.Licensing
import com.example.truckercore.model.shared.errors.InvalidStateException
import com.example.truckercore.model.shared.modules.file.entity.File

/**
 * Represents a licensing record along with associated files.
 * This class encapsulates a [Licensing] entity and its related files.
 * It also validates that the files belong to the provided licensing entity.
 *
 * @property licensing The [Licensing] entity that this record is associated with.
 * @property files A list of [File] entities related to the licensing record. Defaults to an empty list.
 *
 * @throws InvalidStateException if any file does not belong to the provided [Licensing] entity (i.e., the `parentId` of the file does not match the `id` of the [Licensing]).
 */
data class LicensingWithFile(
    val licensing: Licensing,
    val files: List<File> = emptyList()
) {

    init {

        // Validate files
        files.forEach { file ->
            if (file.parentId != licensing.id)
                throw InvalidStateException("File does not belong to the provided licensing.")
        }

    }

    val parentId get() = licensing.parentId

}