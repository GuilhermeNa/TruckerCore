package com.example.truckercore.model.modules.document.crlv.data

import com.example.truckercore._utils.classes.Url
import com.example.truckercore._utils.classes.validity.Validity
import com.example.truckercore.model.modules._shared.contracts.entity.Entity
import com.example.truckercore.model.modules._shared.enums.PersistenceState
import com.example.truckercore.model.modules.company.data.CompanyID
import com.example.truckercore.model.modules.document._shared.Document
import com.example.truckercore.model.modules.fleet._shared.Plate
import java.time.Year

data class Crlv(
    override val id: CrlvID,
    override val companyId: CompanyID,
    override val persistenceState: PersistenceState,
    override val url: Url,
    val validity: Validity,
    val refYear: Year,
    val plate: Plate
) : Entity<Crlv>, Document {

    override fun copyWith(persistence: PersistenceState): Crlv {
        return copy(persistenceState = persistence)
    }

}
