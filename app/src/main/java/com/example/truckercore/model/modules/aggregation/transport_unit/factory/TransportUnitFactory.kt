package com.example.truckercore.model.modules.aggregation.transport_unit.factory

import com.example.truckercore._utils.expressions.getClassName
import com.example.truckercore.model.logger.AppLogger
import com.example.truckercore.model.modules._shared.contracts.factory.Factory
import com.example.truckercore.model.modules.aggregation.transport_unit.data.TransportUnit
import com.example.truckercore.model.modules.aggregation.transport_unit.data.TransportUnitID
import com.example.truckercore.model.modules.aggregation.transport_unit.data.collection.AttachedUnits
import com.example.truckercore.model.modules.fleet.dolly.data.Dolly
import com.example.truckercore.model.modules.fleet.dry_van.data.DryVan
import com.example.truckercore.model.modules.fleet.dump.data.Dump
import com.example.truckercore.model.modules.fleet.grain_trailer.data.GrainTrailer
import com.example.truckercore.model.modules.fleet.truck.data.Truck

object TransportUnitFactory: Factory {

    operator fun invoke(
        transportUnitId: TransportUnitID,
        truck: Truck,
        grainTrailer: GrainTrailer? = null,
        dryVan: DryVan? = null,
        dump: Dump? = null,
        dolly: Dolly? = null
    ): TransportUnit = try {
        val attachedUnit = AttachedUnits().apply {
            grainTrailer?.let { add(it) }
            dryVan?.let { add(it) }
            dump?.let { add(it) }
            dolly?.let { add(it) }
        }

        TransportUnit(
            transportUnitID = transportUnitId,
            tractor = truck,
            attachedUnits = attachedUnit
        )

    } catch (e: Exception) {
        AppLogger.e(getClassName(), "Failed on Transport Unit Creation.", e)
        handleError(e)
    }

}