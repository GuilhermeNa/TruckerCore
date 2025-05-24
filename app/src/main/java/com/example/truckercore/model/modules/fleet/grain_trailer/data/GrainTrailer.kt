package com.example.truckercore.model.modules.fleet.grain_trailer.data

import com.example.truckercore.model.configs.annotations.InternalUseOnly
import com.example.truckercore.model.modules._shared.contracts.entity.Entity
import com.example.truckercore.model.modules._shared.contracts.entity.ID
import com.example.truckercore.model.modules._shared.enums.PersistenceState
import com.example.truckercore.model.modules.company.data.CompanyID
import com.example.truckercore.model.modules.fleet._shared.Plate
import com.example.truckercore.model.modules.fleet._shared.contracts.towable.SemiTrailer

data class GrainTrailer(
    override val id: GrainTrailerID,
    override val companyId: CompanyID,
    override val persistenceState: PersistenceState,
    override val plate: Plate
): Entity<GrainTrailer>,SemiTrailer {

    @InternalUseOnly
    override fun copyWith(persistence: PersistenceState): GrainTrailer {
     return copy(persistenceState = persistence)
    }

}
