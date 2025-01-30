package com.example.truckercore.modules.fleet.shared.abstraction

import com.example.truckercore.shared.modules.storage_file.entity.StorageFile
import java.time.LocalDateTime

abstract class FleetData(
    open val parentId: String,
    open val emissionDate: LocalDateTime,
    open val file: StorageFile? = null
)