package com.example.truckercore.model.modules.fleet.dolly.data

import com.example.truckercore.model.configs.annotations.InternalUseOnly
import com.example.truckercore.model.modules._shared.contracts.entity.Entity
import com.example.truckercore.model.modules._shared.enums.PersistenceState
import com.example.truckercore.model.modules.aggregation.transport_unit.data.TransportUnitID
import com.example.truckercore.model.modules.company.data.CompanyID
import com.example.truckercore.model.modules.fleet._shared.Plate
import com.example.truckercore.model.modules.fleet._shared.contracts.towable.Equipment

data class Dolly(
    override val id: DollyID,
    override val companyId: CompanyID,
    override val persistenceState: PersistenceState,
    override val transportUnitId: TransportUnitID? = null,
    override val plate: Plate
) : Entity<Dolly>, Equipment {

    @InternalUseOnly
    override fun copyWith(persistence: PersistenceState): Dolly {
        return copy(persistenceState = persistence)
    }

}