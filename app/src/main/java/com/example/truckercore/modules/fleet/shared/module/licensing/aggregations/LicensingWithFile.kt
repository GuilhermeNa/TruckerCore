package com.example.truckercore.modules.fleet.shared.module.licensing.aggregations

import com.example.truckercore.modules.fleet.shared.module.licensing.entity.Licensing
import com.example.truckercore.modules.fleet.shared.module.licensing.service.LicensingService
import com.example.truckercore.shared.modules.storage_file.entity.StorageFile

data class LicensingWithFile(
    val licensing: Licensing,
    val files: List<StorageFile> = emptyList()
)