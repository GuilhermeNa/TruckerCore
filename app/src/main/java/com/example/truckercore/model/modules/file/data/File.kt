package com.example.truckercore.model.modules.file.data

import com.example.truckercore._utils.classes.Url
import com.example.truckercore.model.modules.company.data.CompanyID
import com.example.truckercore.model.modules.file.data_helper.FileID
import com.example.truckercore.model.modules._shared.enums.PersistenceState
import com.example.truckercore.model.modules._shared.contracts.entity.Entity

data class File(
    override val id: FileID,
    override val companyId: CompanyID,
    override val persistence: PersistenceState,
    val url: Url
) : Entity