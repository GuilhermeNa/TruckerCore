package com.example.truckercore.modules.fleet.shared.module.licensing.aggregations

import com.example.truckercore.modules.fleet.shared.module.licensing.entity.Licensing
import com.example.truckercore.shared.modules.storage_file.entity.StorageFile

/**
 * Represents a licensing record along with associated files.
 * This class encapsulates a Licensing entity and its related files.
 *
 * @property licensing The licensing entity that this record is associated with.
 * @property files List of files related to the licensing record, defaults to an empty list.
 */
data class LicensingWithFile(
    val licensing: Licensing,
    val files: List<StorageFile> = emptyList()
)