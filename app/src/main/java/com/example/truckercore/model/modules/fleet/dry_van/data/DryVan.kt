package com.example.truckercore.model.modules.fleet.dry_van.data

import com.example.truckercore.model.configs.annotations.InternalUseOnly
import com.example.truckercore.model.modules._shared.contracts.entity.Entity
import com.example.truckercore.model.modules._shared.contracts.entity.ID
import com.example.truckercore.model.modules._shared.enums.PersistenceState
import com.example.truckercore.model.modules.aggregation.transport_unit.data.TransportUnitID
import com.example.truckercore.model.modules.company.data.CompanyID
import com.example.truckercore.model.modules.fleet._shared.Plate
import com.example.truckercore.model.modules.fleet._shared.contracts.towable.SemiTrailer

data class DryVan(
    override val id: DryVanID,
    override val companyId: CompanyID,
    override val persistenceState: PersistenceState,
    override val transportUnitId: TransportUnitID? = null,
    override val plate: Plate
) : Entity<DryVan>, SemiTrailer {

    @InternalUseOnly
    override fun copyWith(persistence: PersistenceState): DryVan {
        return copy(persistenceState = persistence)
    }

}