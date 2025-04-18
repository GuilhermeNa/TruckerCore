package com.example.truckercore.model.modules.file.data

import com.example.truckercore.model.shared.value_classes.Url
import com.example.truckercore.model.modules.company.data_helper.CompanyID
import com.example.truckercore.model.modules.file.data_helper.FileID
import com.example.truckercore.model.shared.enums.Persistence
import com.example.truckercore.model.shared.interfaces.data.entity.Entity

data class File(
    override val id: FileID,
    override val companyId: CompanyID,
    override val persistence: Persistence,
    val url: Url
) : Entity