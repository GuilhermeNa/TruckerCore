package com.example.truckercore.model.modules.file.data

import com.example.truckercore._utils.classes.Url
import com.example.truckercore.model.modules.company.data.CompanyID
import com.example.truckercore.model.modules.file.data_helper.FileID
import com.example.truckercore.model.shared.enums.Persistence
import com.example.truckercore.model.modules._contracts.Entity

data class File(
    override val id: FileID,
    override val companyId: CompanyID,
    override val persistence: Persistence,
    val url: Url
) : Entity