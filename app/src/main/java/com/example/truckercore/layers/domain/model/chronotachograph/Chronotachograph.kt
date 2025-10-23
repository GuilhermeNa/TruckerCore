package com.example.truckercore.layers.domain.model.chronotachograph

import com.example.truckercore.core.my_lib.classes.Url
import com.example.truckercore.layers.domain.base.contracts.entity.ID
import com.example.truckercore.layers.domain.base.contracts.others.Document
import com.example.truckercore.layers.domain.base.enums.PersistenceState
import com.example.truckercore.layers.domain.base.ids.ChronotachographID
import com.example.truckercore.layers.domain.base.ids.CompanyID

data class Chronotachograph(
    override val id: ChronotachographID,
    override val url: Url,
    override val companyId: CompanyID,
    override val persistence: PersistenceState
): Document {


}