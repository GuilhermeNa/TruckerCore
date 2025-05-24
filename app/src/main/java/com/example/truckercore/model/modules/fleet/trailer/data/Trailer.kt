package com.example.truckercore.model.modules.fleet.trailer.data

import com.example.truckercore.model.configs.annotations.InternalUseOnly
import com.example.truckercore.model.modules._shared.contracts.entity.Entity
import com.example.truckercore.model.modules._shared.enums.PersistenceState
import com.example.truckercore.model.modules.company.data.CompanyID
import com.example.truckercore.model.modules.fleet._shared.Plate
import com.example.truckercore.model.modules.fleet._shared.contracts.FleetComponent

data class Trailer(
    override val id: TrailerID,
    override val companyId: CompanyID,
    override val persistenceState: PersistenceState,
    override val plate: Plate
): Entity<Trailer>, FleetComponent {

    @InternalUseOnly
    override fun copyWith(persistence: PersistenceState): Trailer {
        return this.copy(persistenceState = persistence)
    }

}