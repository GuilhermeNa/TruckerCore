package com.example.truckercore.model.modules.fleet.truck.aggregation

import com.example.truckercore.model.modules.fleet.shared.module.licensing.aggregations.LicensingWithFile
import com.example.truckercore.model.modules.fleet.trailer.aggregations.TrailerWithDetails
import com.example.truckercore.model.modules.fleet.truck.entity.Truck
import com.example.truckercore.model.shared.errors.InvalidStateException

/**
 * Represents a truck along with its associated trailers and licensing records.
 * This class encapsulates the [Truck] entity along with a list of [TrailerWithDetails]
 * entities and a list of [LicensingWithFile] entities.
 *
 * @property truck The main [Truck] entity that contains essential information about the truck.
 * @property trailersWithDetails A list of [TrailerWithDetails] entities, each representing a trailer
 *                               associated with the truck. Defaults to an empty list.
 * @property licensingWithFiles A list of [LicensingWithFile] entities, each representing a licensing record
 *                               with related files associated with the truck. Defaults to an empty list.
 *
 * @throws InvalidStateException if any of the trailers or licensing records do not belong to the provided truck.
 */
data class TruckWithDetails(
    val truck: Truck,
    val trailersWithDetails: List<TrailerWithDetails> = emptyList(),
    val licensingWithFiles: List<LicensingWithFile> = emptyList()
) {

    init {
        // Validate if the trailers belong to the provided truck
        trailersWithDetails.forEach { trailerWD ->
            if (trailerWD.truckId != truck.id) {
                throw InvalidStateException("Trailer does not belong to the provided truck.")
            }
        }

        // Validate if the licensing records belong to the provided truck
        licensingWithFiles.forEach { licensingWF ->
            if (licensingWF.parentId != truck.id) {
                throw InvalidStateException("Licensing does not belong to the provided truck.")
            }
        }
    }

}
