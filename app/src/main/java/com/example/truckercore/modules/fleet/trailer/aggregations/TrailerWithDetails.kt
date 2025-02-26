package com.example.truckercore.modules.fleet.trailer.aggregations

import com.example.truckercore.modules.fleet.shared.module.licensing.aggregations.LicensingWithFile
import com.example.truckercore.modules.fleet.shared.module.licensing.entity.Licensing
import com.example.truckercore.modules.fleet.trailer.entity.Trailer
import com.example.truckercore.shared.errors.InvalidStateException

/**
 * Represents a trailer along with its associated licensing records and files.
 * This class encapsulates the [Trailer] entity and a list of associated [LicensingWithFile] records.
 *
 * @property trailer The main [Trailer] entity containing essential information about the trailer.
 * @property licensingWithFiles A list of [LicensingWithFile] entities, each representing a licensing record with related files.
 *
 * @throws InvalidStateException if any of the licensing records or files do not belong to the provided trailer.
 */
data class TrailerWithDetails(
    val trailer: Trailer,
    val licensingWithFiles: List<LicensingWithFile> = emptyList()
) {

    init {

        // Validate if the licensing records belong to the provided trailer
        licensingWithFiles.forEach { licensingWF ->
            if (licensingWF.parentId != trailer.id) {
                throw InvalidStateException("Licensing does not belong to the provided trailer.")
            }
        }

    }

    val truckId get() = trailer.truckId

}
